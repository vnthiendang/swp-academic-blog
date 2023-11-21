package com.swp.cms.controllers;

import com.swp.cms.dto.PostApprovalsDto;
import com.swp.cms.dto.PostDto;
import com.swp.cms.reqDto.PostApprovalsRequest;
import com.swp.entities.PostApprovals;
import com.swp.services.PostApprovalsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/postapproval")
public class PostApprovalsController {

    private final PostApprovalsService postApprovalsService;
    @Autowired
    private ModelMapper modelMapper;


    public PostApprovalsController(PostApprovalsService postApprovalsService) {
        this.postApprovalsService = postApprovalsService;
    }

    @GetMapping("/getall")
    public List<PostApprovalsDto> getAll(
            @RequestParam(name = "postApprovalStatuses", required = false) List<String> postApprovalStatuses,
            @RequestParam(name = "postCategories", required = false) List<String> postCategories,
            @RequestParam(name = "isFilterByCurrentUserCategoryManagement", required = false) Boolean isFilterByCurrentUserCategoryManagement,
            @RequestParam(name = "isExcludedCurrentUserOwnPostRequest", required = false) Boolean isExcludedCurrentUserOwnPostRequest,
            @RequestParam(name = "isExcludedOtherUserPostRequest", required = false) Boolean isExcludedOtherUserPostRequest,
            @RequestParam(name = "userIdOfPostOfPostApproval", required = false) Integer userIdOfPostOfPostApproval,
            @RequestParam(name = "startDate", required = false) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false) LocalDateTime endDate,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdDate") String sortBy,
            @RequestParam(name = "teacherId", required = false) Integer teacherId,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "desc") String sortDirection
    ) {
        // Check authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<PostApprovals> postApprovals = postApprovalsService.filterPostApprovalsByPostApprovalStatus(postApprovalStatuses);

        if (postCategories != null && !postCategories.isEmpty()) {
            postApprovals = postApprovalsService.filterPostApprovalsByPostCategories(postApprovals, postCategories);
        }

        if (isFilterByCurrentUserCategoryManagement != null && isFilterByCurrentUserCategoryManagement) {
            postApprovals = postApprovalsService.filterByCurrentUserCategoryManagement(postApprovals);
        }

        if (isExcludedCurrentUserOwnPostRequest != null && isExcludedCurrentUserOwnPostRequest){
            postApprovals = postApprovalsService.filterExcludedCurrentUserOwnPostRequest(postApprovals);
        }

        if (isExcludedOtherUserPostRequest != null && isExcludedOtherUserPostRequest){
            postApprovals = postApprovalsService.filterExcludedOtherUserPostRequest(postApprovals);
        }

        if (userIdOfPostOfPostApproval != null){
            postApprovals = postApprovalsService.GetPostApprovalsByUserIdOfPostOfPostApproval(postApprovals, userIdOfPostOfPostApproval);
        }

        if (startDate != null && endDate != null) {
            postApprovals = postApprovalsService.filterPostsByDateRange(postApprovals, startDate, endDate);
        }

        if (teacherId != null){
            postApprovals = postApprovalsService.GetPostApprovalsByTeacherId(postApprovals, teacherId);
        }

        postApprovals = postApprovalsService.sortPostApprovals(postApprovals, sortBy, sortDirection);

        List<PostApprovalsDto> dtos = postApprovalsService.mapPostApprovalsToPostApprovalDtos(postApprovals);
        return dtos;

    }

    //Get post_approvals by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {

            PostApprovals postApprovals = postApprovalsService.getById(id);
            PostApprovalsDto dto = modelMapper.map(postApprovals, PostApprovalsDto.class);
            return ResponseEntity.ok(dto);
        } catch (NoSuchElementException ex) {
            // PostApprovals not found with the given ID
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            // Handle other exceptions as needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    //Post a post_approvals
    @PostMapping("/post")
    public PostApprovalsDto addPostApprovals(@RequestBody PostApprovalsRequest postApprovalsRequest) {
        PostApprovals createdPostApprovals = postApprovalsService.createPostApprovals(postApprovalsRequest);
        PostApprovalsDto postApprovalsDto = modelMapper.map(createdPostApprovals, PostApprovalsDto.class);
        return postApprovalsDto;
    }

    //Update a post_approvals by postApprovalsId
    @PutMapping("/{postApprovalsId}")
    public PostApprovalsDto updatePostApprovals(@PathVariable Integer postApprovalsId, @RequestBody PostApprovalsRequest postApprovalsRequest) {
        PostApprovals updatedPostApprovals = postApprovalsService.updatePostApprovals(postApprovalsId, postApprovalsRequest);
        PostApprovalsDto postApprovalsDto = modelMapper.map(updatedPostApprovals, PostApprovalsDto.class);
        return postApprovalsDto;
    }

}
