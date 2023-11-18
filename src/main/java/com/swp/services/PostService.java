package com.swp.services;

import com.swp.cms.dto.PostDto;
import com.swp.cms.reqDto.PostRequest;
import com.swp.entities.*;
import com.swp.repositories.*;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostApprovalsRepository postApprovalsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ReportTypeRepository reportTypeRepository;

    @Autowired
    private CategoryManagementRepository categoryManagementRepository;


    @Autowired
    private ModelMapper modelMapper;
    private Integer userId;

    @PostConstruct
    public void initialize() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User userDetails = (User) authentication.getPrincipal();
            userId = userDetails.getUsId();
        }
    }
@Transactional
    public Post getById(Integer id) {
        List<Post> approvedPosts = postRepository.findAllApprovedPosts();
        Optional<Post> optionalPost = approvedPosts.stream()
                .filter(post -> post.getPostsId().equals(id))
                .findFirst();

        return optionalPost.orElse(null);
    }

    public Post getPostById(Integer id) {
        return postRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return postRepository.existsById(id);
    }

    public void deleteById(int id) {
        postRepository.deleteById(id);
    }

    public Post add(Post post) {
        return postRepository.save(post);
    }
    @Transactional
    public List<Post> getAllApprovedPosts() {
        return postRepository.findAllApprovedPosts();
    }
    @Transactional
    public List<Post> getAll() {
        return postRepository.findAllReviewedPosts();
    }

    public Post createPost(PostRequest postRequest) {
        Post post = new Post();
        post.setCreatedByUser(userRepository.findById(postRequest.getUserID())
                .orElseThrow(() -> new IllegalArgumentException("Invalid User")));
        post.setBelongedToCategory(categoryRepository.findByContent(postRequest.getCategoryName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category")));
        post.setTitle(postRequest.getTitle());
        post.setPostDetail(postRequest.getDetail());
        post.setCreatedTime(LocalDateTime.now());

        List<MultipartFile> medias = postRequest.getMediaList(); // Change to a list of media URLs
        if (medias != null && !medias.isEmpty()) {
            List<Media> mediaList = new ArrayList<>();
            for (MultipartFile media : medias) {
                if (!media.isEmpty()){
                    Media media1 = new Media();
                    media1.setPost(post);
                    media1.setMediaUrl("ko co URL");
                    media1.setName(media.getOriginalFilename()); // Set the name from the uploaded file
                    media1.setContentType(media.getContentType());
                    try {
                        // Read the bytes from the uploaded file and compress the image
                        byte[] compressedImageData = ImageUtils.compressImage(media.getBytes());

                        // Set the compressed image data
                        media1.setData(compressedImageData);

                        // Save the media object to the repository
//                        media1 = mediaRepository.save(media1);

                        mediaList.add(media1);
                    } catch (IOException e) {
                        // Handle any potential IO errors
                        e.printStackTrace(); // You might want to log or handle the exception appropriately
                    }
                }
            }
            post.setMedias(mediaList);
        }

        List<String> tagNames = postRequest.getTagList();
        if (tagNames != null && !tagNames.isEmpty()) {
            List<PostTag> postTagList = new ArrayList<>();
            for (String tagName : tagNames) {
                Tag tag = tagRepository.findByTagName(tagName)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Tag"));
                PostTag postTag = new PostTag();
                postTag.setTag(tag);
                postTag.setPost(post);
                postTagList.add(postTag);
            }
            post.setPostTags(postTagList);
        }
        return postRepository.save(post);
    }

@Transactional
    public Post updatePost(Integer postId, PostRequest postRequest) {
        Post post = getById(postId);
        if (post == null) {
            throw new IllegalArgumentException("Post not found"); // Or handle it as needed
        }

        post.setTitle(postRequest.getTitle());
        post.setPostDetail(postRequest.getDetail());
        post.setBelongedToCategory(categoryRepository.findByContent(postRequest.getCategoryName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category")));

        // Update Medias
        List<MultipartFile> medias = postRequest.getMediaList();
        if (medias != null) {
            // Clear existing media
            post.getMedias().clear();

            List<Media> mediaList = new ArrayList<>();

            for (MultipartFile media : medias) {
                if (!media.isEmpty()) {
                    Media media1 = new Media();
                    media1.setPost(post);
                    media1.setName(media.getOriginalFilename()); // Set the name from the uploaded file
                    media1.setContentType(media.getContentType());
                    try {
                        // Read the bytes from the uploaded file and compress the image
                        byte[] compressedImageData = ImageUtils.compressImage(media.getBytes());

                        // Set the compressed image data
                        media1.setData(compressedImageData);

                        // Save the media object to the repository
                        //media1 = mediaRepository.save(media1);

                        mediaList.add(media1);
                    } catch (IOException e) {
                        // Handle any potential IO errors
                        e.printStackTrace(); // You might want to log or handle the exception appropriately
                    }
                }
            }

            // Add the new media to the post
            post.getMedias().addAll(mediaList);
        }

        // Update PostTags
        List<String> tagNames = postRequest.getTagList();
        if (tagNames != null && !tagNames.isEmpty()) {
            // Clear existing post tags and replace with new ones
            post.getPostTags().clear();
            List<PostTag> postTagList = new ArrayList<>();
            for (String tagName : tagNames) {
                Tag tag = tagRepository.findByTagName(tagName)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Tag"));
                PostTag postTag = new PostTag();
                postTag.setTag(tag);
                postTag.setPost(post);
                postTagList.add(postTag);
            }
            post.setPostTags(postTagList);
        } else {
            // If tagIds is empty, remove all existing post tags
            post.getPostTags().clear();
        }

        Optional<PostApprovals> postApprovals = postApprovalsRepository.findByPostPostsId(postId);

        if (postApprovals.isPresent()){
            postApprovalsRepository.deleteById(postApprovals.get().getId());
            post.setPostApprovals(null);
        }
        return postRepository.save(post); // Save and return the updated post

    }

@Transactional
    public List<Post> findMostVotedPostInCategory(int categoryId) {
        // Step 1: Fetch all posts in the desired category
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Tag"));
        List<Post> postsInCategory = postRepository.findByBelongedToCategory(category);

        // Step 2: Find the post with the most votes
        postsInCategory.sort((post1, post2) -> Integer.compare(
            post2.getVotes().size(),
            post1.getVotes().size()));

        return postsInCategory;
    }


    //DISPLAY APPROVED POSTS
    public List<Post> searchPosts(String keyword) {
        return postRepository.searchApprovedPosts(keyword);
    }

    //SAVE MEDIA AND TAG
//    @Transactional
//    public Post savePosts(Post createdPost) {
//
//        List<Media> mediaList = createdPost.getMedias();
//        if (mediaList != null && !mediaList.isEmpty()) {
//            for (Media media : mediaList) {
//                media.setPost(createdPost);
//                entityManager.persist(media);
//            }
//        }
//
//        List<PostTag> tagList = createdPost.getTags();
//        if (tagList != null && !tagList.isEmpty()) {
//            for (PostTag tag : tagList) {
//                tag.setPost(createdPost);
//                entityManager.persist(tag);
//            }
//        }
//
//        return createdPost;
//    }

    //approve post
    public PostApprovals approvePost(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        Integer userIds = userDetails.getUsId();

        User currentUser = userRepository.findById(userIds)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User"));
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()){
            if (post.get().getCreatedByUser().getUsId() == userIds ){
                throw new IllegalStateException("Cannot review your own post");
            }
        }
        PostApprovals postApprovals = new PostApprovals();
        postApprovals.setPost(getPostById(id));
        postApprovals.setStatus("APPROVED");
        postApprovals.setCreatedDate(LocalDateTime.now());
        postApprovals.setViewedByUser(currentUser);
        return postApprovalsRepository.save(postApprovals);
    }

    //reject post
    public PostApprovals rejectPost(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        Integer userIds = userDetails.getUsId();

        User currentUser = userRepository.findById(userIds)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User"));
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()){
            if (post.get().getCreatedByUser().getUsId() == userIds ){
                throw new IllegalStateException("Cannot review your own post");
            }
        }
        PostApprovals postApprovals = new PostApprovals();
        postApprovals.setPost(getPostById(id));
        postApprovals.setStatus("REJECTED");
        postApprovals.setCreatedDate(LocalDateTime.now());
        postApprovals.setViewedByUser(currentUser);
        return postApprovalsRepository.save(postApprovals);
    }


    public long countRejectedPostApprovalsForUserWithin24Hours(Integer userId) {
        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);


        return postApprovalsRepository.countByPost_CreatedByUser_UsIdAndStatusAndCreatedDateGreaterThan(
                userId, "REJECTED", twentyFourHoursAgo
        );
    }

    public Report generateReportIfCountRejectedPostApprovalsForUserWithin24HoursThresholdExceeded(Integer userId) {
        long rejectedPostApprovalsCount = countRejectedPostApprovalsForUserWithin24Hours(userId);

        if (rejectedPostApprovalsCount >= 5) {
            // Create a Report object or take appropriate actions
            Report report = new Report();
            report.setReportType(reportTypeRepository.findById(2).orElseThrow());
            report.setReportDetail("User: " + userRepository.findById(userId).orElseThrow().getDisplay_name()
            + " has more than 4 rejected post requests within 24 hours");
            report.setCreatedTime(LocalDateTime.now());
            return reportRepository.save(report);
        }

        return null; // No report generated if the threshold is not exceeded
    }


    public List<PostDto> mapPostsToPostDtos(List<Post> posts) {
        return posts.stream()
                .map(post -> {
                    PostDto postDto = modelMapper.map(post, PostDto.class);
                    return postDto;
                })
                .collect(Collectors.toList());
    }

    public PostDto mapPostToPostDto(Post post) {
        PostDto dto = modelMapper.map(post, PostDto.class);
        // Map mediaList and postTagList
        return dto;
    }

    public List<Post> filterByCategoryId(List<Post> approvedPosts, Integer categoryId) {
        if (categoryId == null) {
            return approvedPosts;
        } else {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + categoryId));
            return approvedPosts.stream()
                    .filter(post -> post.getBelongedToCategory().getCateId().equals(categoryId))
                    .collect(Collectors.toList());
        }
    }

    public List<Post> filterByTagIds(List<Post> approvedPosts, List<Integer> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return approvedPosts;
        } else {
            for (Integer tagId : tagIds) {
                if (tagRepository.findById(tagId).isEmpty()) {
                    throw new IllegalArgumentException("Tag not found with ID: " + tagId);
                }
            }

            return approvedPosts.stream()
                    .filter(post -> new HashSet<>(post.getPostTags().stream()
                            .map(PostTag::getTag)
                            .map(Tag::getId)
                            .collect(Collectors.toList()))
                            .containsAll(tagIds))
                    .collect(Collectors.toList());
        }
    }

    public List<Post> filterByTagNames(List<Post> approvedPosts, List<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty()) {
            return approvedPosts;
        } else {
            for (String tagName : tagNames) {
                if (tagRepository.findByTagName(tagName).isEmpty()) {
                    throw new IllegalArgumentException("Tag not found with ID: " + tagName);
                }
            }

            return approvedPosts.stream()
                    .filter(post -> postContainsAllTagNames(post, tagNames))
                    .collect(Collectors.toList());
        }
    }
    private boolean postContainsAllTagNames(Post post, List<String> tagNames) {
        Set<String> postTags = post.getPostTags().stream()
                .map(PostTag::getTag)
                .map(Tag::getTagName)
                .collect(Collectors.toSet());

        return postTags.containsAll(tagNames);
    }


    public List<Post> GetPostsByTagId(List<Post> approvedPosts, Integer tagId) {
        // Check if the tagId is valid
        if (tagRepository.findById(tagId).isEmpty()) {
            throw new IllegalArgumentException("Tag not found with ID: " + tagId);
        }

        return approvedPosts.stream()
                .filter(post -> post.getPostTags().stream()
                        .map(PostTag::getTag)
                        .map(Tag::getId)
                        .anyMatch(id -> id.equals(tagId))
                )
                .collect(Collectors.toList());
    }

    public List<Post> GetPostsByKeyword(List<Post> approvedPosts, String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            String lowercaseKeyword = keyword.toLowerCase().trim();
            return approvedPosts.stream()
                    .filter(post -> post.getTitle().toLowerCase().contains(lowercaseKeyword))
                    .collect(Collectors.toList());
        }
        return approvedPosts; // Return the original list if keyword is not provided
    }

    public List<Post> getPostsWithoutApprovals() {
        List<PostApprovals> postApprovals = postApprovalsRepository.findAll();

        List<Integer> postIdsWithApprovals = postApprovals
                .stream()
                .map(postApproval -> postApproval.getPost().getPostsId())
                .collect(Collectors.toList());


        return postRepository.findAll()
                .stream()
                .filter(post -> !postIdsWithApprovals.contains(post.getPostsId()))
                .collect(Collectors.toList());
    }

    public List<Post> filterPostsByDateRange(List<Post> approvedPosts, LocalDateTime startDate, LocalDateTime endDate) {
        return approvedPosts.stream()
                .filter(post -> post.getCreatedTime().isAfter(startDate) && post.getCreatedTime().isBefore(endDate))
                .collect(Collectors.toList());
    }

    public List<Post> sortPosts(List<Post> approvedPosts, String sortBy, String sortDirection) {
        List<Post> sortedPosts = new ArrayList<>(approvedPosts);

        switch (sortBy) {
            case "likeCount":
                sortedPosts.sort(Comparator.comparingInt(post -> (int) post.getVotes().stream().filter(vote -> vote.getVoteType().getId() == 1).count()));
                break;
            case "dislikeCount":
                sortedPosts.sort(Comparator.comparingInt(post -> (int) post.getVotes().stream().filter(vote -> vote.getVoteType().getId() == 2).count()));
                break;
            case "createdDate":
                sortedPosts.sort(Comparator.comparing(Post::getCreatedTime));
                break;
            case "awardCount":
                sortedPosts.sort(Comparator.comparingInt(post -> post.getAwards().size()));
                break;
            default:
                // Handle unsupported sortBy criteria.
                return approvedPosts;
        }

        if ("desc".equalsIgnoreCase(sortDirection)) {
            Collections.reverse(sortedPosts);
        }

        return sortedPosts;
    }

    public List<Post> filterByCategoryName(List<Post> approvedPosts, String categoryName) {
        if (categoryName == null) {
            return approvedPosts;
        } else {
            Category category = categoryRepository.findByContent(categoryName)
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with name: " + categoryName));
            return approvedPosts.stream()
                    .filter(post -> post.getBelongedToCategory().getContent().equals(categoryName))
                    .collect(Collectors.toList());
        }
    }

    public List<Post> GetPostsByCategoryId(List<Post> approvedPosts, Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + categoryId));
        return approvedPosts.stream()
                .filter(post -> post.getBelongedToCategory().getCateId().equals(categoryId))
                .collect(Collectors.toList());
    }
    public List<Post> GetPostsByCategoryName(List<Post> approvedPosts, String categoryName) {
        Category category = categoryRepository.findByContent(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with Name: " + categoryName));
        return approvedPosts.stream()
                .filter(post -> post.getBelongedToCategory().getContent().equals(categoryName))
                .collect(Collectors.toList());
    }

    public List<Post> GetPostsByTagName(List<Post> approvedPosts, String tagName) {
        // Check if the tagName is valid
        if (tagRepository.findByTagName(tagName).isEmpty()) {
            throw new IllegalArgumentException("Tag not found with name: " + tagName);
        }

        return approvedPosts.stream()
                .filter(post -> post.getPostTags().stream()
                        .map(PostTag::getTag)
                        .map(Tag::getTagName)
                        .anyMatch(name -> name.equals(tagName))
                )
                .collect(Collectors.toList());
    }

    public List<Post> GetPostsByUserId(List<Post> approvedPosts, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with userId: " + userId));
        return approvedPosts.stream()
                .filter(post -> post.getCreatedByUser().getUsId().equals(userId))
                .collect(Collectors.toList());
    }



    public List<Post> filterPostsByPostStatus(List<Post> posts, List<String> postStatuses) {
        List<Post> results = new ArrayList<>();

        if (postStatuses != null && !postStatuses.isEmpty()) {
            for (String postStatus : postStatuses) {
                switch (postStatus.toLowerCase().trim()) {
                    case "edited":
                        results.addAll(posts.stream()
                                .filter(post -> "edited".equalsIgnoreCase(post.getStatus()))
                                .collect(Collectors.toList()));
                        break;

                    case "deleted":
                        results.addAll(posts.stream()
                                .filter(post -> "deleted".equalsIgnoreCase(post.getStatus()))
                                .collect(Collectors.toList()));
                        break;

                    case "created":
                        results.addAll(posts.stream()
                                .filter(post -> post.getStatus() == null)
                                .collect(Collectors.toList()));
                        break;

                    default:
                        throw new IllegalArgumentException("Unsupported post status: " + postStatus);

                }
            }
        }

        return results;
    }

    public List<Post> filterPostsByPostApprovalStatus(List<String> postApprovalStatuses) {
        List<Post> results = new ArrayList<>();
        if (postApprovalStatuses != null && !postApprovalStatuses.isEmpty()) {

            for (String postApprovalStatus : postApprovalStatuses) {
                switch (postApprovalStatus.toLowerCase().trim()) {
                    case "approved":
                        results.addAll(postRepository.findAllApprovedPosts());
                        break;

                    case "rejected":
                        results.addAll(postRepository.findAllRejectedPosts());
                        break;

                    case "pending":
                        results.addAll(getPostsWithoutApprovals());
                        break;

                    default:
                        throw new IllegalArgumentException("Unsupported post approval status: " + postApprovalStatus);
                }
            }
        }
        return results;
    }

    public List<Post> getPostRequestsWithoutCurrentUserOwnPostRequests(List<Post> posts) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        Integer userIds = userDetails.getUsId();

        List<Post> postsWithoutCurrentUserOwnPostRequests = posts.stream()
                .filter(post -> !post.getCreatedByUser().getUsId().equals(userIds))
                .collect(Collectors.toList());

        return postsWithoutCurrentUserOwnPostRequests;
    }

    public List<Post> getPostsWithoutApprovalsAndByCurrentUserCategoryManagement() {
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

        List<PostApprovals> postApprovals = postApprovalsRepository.findAll();

        List<PostApprovals> postApprovalsFiltered = new ArrayList<>();
        if (categoryManagementOfCurrentUser != null && !categoryManagementOfCurrentUser.isEmpty()) {
            postApprovalsFiltered = postApprovals.stream()
                    .filter(postApproval ->
                            categoryManagementOfCurrentUser.contains(postApproval.getPost().getBelongedToCategory().getCateId()))
                    .collect(Collectors.toList());
        }

        List<Integer> postIdsWithApprovals = postApprovalsFiltered.stream()
                .map(postApproval -> postApproval.getPost().getPostsId())
                .collect(Collectors.toList());

        return postRepository.findAll()
                .stream()
                .filter(post -> !postIdsWithApprovals.contains(post.getPostsId()))
                .filter(post -> categoryManagementOfCurrentUser.contains(post.getBelongedToCategory().getCateId()))
                .collect(Collectors.toList());
    }

}
