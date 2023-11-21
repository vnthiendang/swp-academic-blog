package com.swp.services;

import com.swp.cms.dto.PostApprovalsDto;
import com.swp.cms.dto.PostDto;
import com.swp.cms.reqDto.PostApprovalsRequest;
import com.swp.entities.*;
import com.swp.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostApprovalsService {
    @Autowired
    private PostApprovalsRepository postApprovalsRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryManagementRepository categoryManagementRepository;

    public PostApprovals getById(int id) {
        return postApprovalsRepository.findById(id).orElseThrow();
    }


    public Boolean existsById(int id) {
        return postApprovalsRepository.existsById(id);
    }

    public void deleteById(int id) {
        postApprovalsRepository.deleteById(id);
    }

    public PostApprovals add(PostApprovals award) {
        return postApprovalsRepository.save(award);
    }

    public List<PostApprovals> getAll() {
        return postApprovalsRepository.findAll();
    }
    public PostApprovals createPostApprovals(PostApprovalsRequest postApprovalsRequest){
        PostApprovals postApprovals = new PostApprovals();
        postApprovals.setPost(postRepository.findById(postApprovalsRequest.getPost()).
                orElseThrow(() -> new IllegalArgumentException("Invalid Post")));
        postApprovals.setViewedByUser(userRepository.findById(postApprovalsRequest.getViewedByUser()).
                orElseThrow(() -> new IllegalArgumentException("Invalid User")));
        postApprovals.setTeacherMessage(postApprovalsRequest.getTeacherMessage());
        postApprovals.setCreatedDate(LocalDateTime.now());
        postApprovals.setStatus(postApprovalsRequest.getPostApprovalsStatus());
        return postApprovalsRepository.save(postApprovals);
    }
    public PostApprovals updatePostApprovals(Integer postApprovalsID, PostApprovalsRequest postApprovalsRequest){
        PostApprovals postApprovals = getById(postApprovalsID);
        postApprovals.setViewedByUser(userRepository.findById(postApprovalsRequest.getViewedByUser()).
                orElseThrow(() -> new IllegalArgumentException("Invalid User")));
        postApprovals.setStatus(postApprovalsRequest.getPostApprovalsStatus());
        postApprovals.setTeacherMessage(postApprovalsRequest.getTeacherMessage());
        postApprovals.setCreatedDate(LocalDateTime.now());
        return postApprovalsRepository.save(postApprovals); // Save and return the updated post
    }


    public List<PostApprovals> filterPostApprovalsByPostApprovalStatus(List<String> postApprovalStatuses) {
        List<PostApprovals> results = new ArrayList<>();
        if (postApprovalStatuses != null && !postApprovalStatuses.isEmpty()) {

            for (String postApprovalStatus : postApprovalStatuses) {
                switch (postApprovalStatus.toLowerCase().trim()) {
                    case "approved":
                        results.addAll(postApprovalsRepository.findByStatus("approved"));
                        break;

                    case "rejected":
                        results.addAll(postApprovalsRepository.findByStatus("approved"));
                        break;

                    default:
                        throw new IllegalArgumentException("Unsupported post approval status: " + postApprovalStatus);
                }
            }
        }
        return results;
    }

    public List<PostApprovals> filterPostApprovalsByPostCategories(List<PostApprovals> postApprovals, List<String> postCategories) {
        List<PostApprovals> results = new ArrayList<>();
        if (postCategories != null && !postCategories.isEmpty()) {
            for (String postCategory : postCategories) {
                Category category = categoryRepository.findByContent(postCategory.toLowerCase().trim())
                        .orElseThrow(() -> new IllegalArgumentException("Category not found: " + postCategory));
                results.addAll(postApprovalsRepository.findByPostBelongedToCategoryContent(postCategory));
            }
            return results;
        }else {
            return postApprovals;
        }
    }

    public List<PostApprovalsDto> mapPostApprovalsToPostApprovalDtos(List<PostApprovals> postApprovals) {
        return postApprovals.stream()
                .map(postApproval -> {
                    PostApprovalsDto postApprovalDto = modelMapper.map(postApproval, PostApprovalsDto.class);
                    return postApprovalDto;
                })
                .collect(Collectors.toList());
    }

    public List<PostApprovals> sortPostApprovals(List<PostApprovals> postApprovals, String sortBy, String sortDirection) {
        List<PostApprovals> sortedPostApprovals = new ArrayList<>(postApprovals);

        switch (sortBy) {
//            case "likeCount":
//                sortedPostApprovals.sort(Comparator.comparingInt(post -> (int) post.getVotes().stream().filter(vote -> vote.getVoteType().getId() == 1).count()));
//                break;
//            case "dislikeCount":
//                sortedPostApprovals.sort(Comparator.comparingInt(post -> (int) post.getVotes().stream().filter(vote -> vote.getVoteType().getId() == 2).count()));
//                break;
            case "createdDate":
                sortedPostApprovals.sort(Comparator.comparing(PostApprovals::getCreatedDate));
                break;
//            case "awardCount":
//                sortedPostApprovals.sort(Comparator.comparingInt(post -> post.getAwards().size()));
//                break;
            default:
                // Handle unsupported sortBy criteria.
                return postApprovals;
        }

        if ("desc".equalsIgnoreCase(sortDirection)) {
            Collections.reverse(sortedPostApprovals);
        }

        return sortedPostApprovals;
    }

    public List<PostApprovals> GetPostApprovalsByTeacherId(List<PostApprovals> postApprovals, Integer teacherId) {
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with userId: " + teacherId));
        return postApprovals.stream()
                .filter(postApproval -> postApproval.getViewedByUser().getUsId().equals(teacherId))
                .collect(Collectors.toList());
    }

    public List<PostApprovals> filterPostsByDateRange(List<PostApprovals> postApprovals, LocalDateTime startDate, LocalDateTime endDate) {
        return postApprovals.stream()
                .filter(postApproval -> postApproval.getCreatedDate().isAfter(startDate) && postApproval.getCreatedDate().isBefore(endDate))
                .collect(Collectors.toList());
    }

    public List<PostApprovals> filterByCurrentUserCategoryManagement(List<PostApprovals> postApprovals) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        Integer userIds = userDetails.getUsId();

        User currentUser = userRepository.findById(userIds)
                .orElseThrow(() -> new IllegalArgumentException("User not found with userId: " + userIds));
        List<CategoryManagement> categoryManagements = categoryManagementRepository.findAll();
        List<Integer> categoryManagementOfCurrentUser = categoryManagements.stream()
                .filter(categoryManagement -> categoryManagement.getTeacher().getUsId().equals(userIds))
                .map(CategoryManagement::getId)
                .collect(Collectors.toList());

        List<PostApprovals> postApprovalss = postApprovalsRepository.findAll();

        List<PostApprovals> postApprovalsFiltered = new ArrayList<>();
        if (categoryManagementOfCurrentUser != null && !categoryManagementOfCurrentUser.isEmpty()) {
            return postApprovalsFiltered = postApprovalss.stream()
                    .filter(postApproval ->
                            categoryManagementOfCurrentUser.contains(postApproval.getPost().getBelongedToCategory().getCateId()))
                    .collect(Collectors.toList());
        } else {
            return postApprovals;
        }
    }

    public List<PostApprovals> filterExcludedCurrentUserOwnPostRequest(List<PostApprovals> postApprovals) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        Integer userIds = userDetails.getUsId();

        List<PostApprovals> postApprovalsWithoutCurrentUserOwnPostRequests = postApprovals.stream()
                .filter(postApproval -> !postApproval.getPost().getCreatedByUser().getUsId().equals(userIds))
                .collect(Collectors.toList());

        return postApprovalsWithoutCurrentUserOwnPostRequests;
    }

    public List<PostApprovals> filterExcludedOtherUserPostRequest(List<PostApprovals> postApprovals) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        Integer userIds = userDetails.getUsId();

        List<PostApprovals> postApprovalsExcludedOtherUserPostRequest = postApprovals.stream()
                .filter(postApproval -> postApproval.getPost().getCreatedByUser().getUsId().equals(userIds))
                .collect(Collectors.toList());

        return postApprovalsExcludedOtherUserPostRequest;
    }

    public List<PostApprovals> GetPostApprovalsByUserIdOfPostOfPostApproval(List<PostApprovals> postApprovals, Integer userIdOfPostOfPostApproval) {
        User currentUser = userRepository.findById(userIdOfPostOfPostApproval)
                .orElseThrow(() -> new IllegalArgumentException("User not found with userId: " + userIdOfPostOfPostApproval));
        return postApprovals.stream()
                .filter(postApproval -> postApproval.getPost().getCreatedByUser().getUsId().equals(userIdOfPostOfPostApproval))
                .collect(Collectors.toList());
    }

}
