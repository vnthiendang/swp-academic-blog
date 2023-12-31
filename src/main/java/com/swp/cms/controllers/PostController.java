package com.swp.cms.controllers;

import com.swp.cms.dto.PostApprovalsDto;
import com.swp.cms.dto.PostDto;
import com.swp.cms.mapper.PostMapper;
import com.swp.cms.reqDto.PostApprovalsRequest;
import com.swp.cms.reqDto.PostRequest;
import com.swp.entities.Post;
import com.swp.entities.PostApprovals;
import com.swp.entities.Report;

import com.swp.services.PostApprovalsService;

import com.swp.services.PostService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blog/post")
public class PostController {
    @Autowired
    private final PostService postService;
    @Autowired
    private ModelMapper modelMapper;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    // ==================================================================================================== //

    // STUDENT ROLE

    //create post (upload mediaUrl & tag)
    @PostMapping("/create")
    public PostDto addPost(@ModelAttribute PostRequest postRequest) {
        Post post = postService.createPost(postRequest);
        PostDto dto = postService.mapPostToPostDto(post);
        return dto;
    }


    //update a post by postId
    @PutMapping("/edit/{id}")
    public PostDto updatePost(@PathVariable Integer id, @ModelAttribute PostRequest postRequest){
        Post post = postService.updatePost(id, postRequest);
        PostDto dto = postService.mapPostToPostDto(post);
        return dto;
    }

    //search posts by title, detail, postgory
    @GetMapping("/search")
    public List<PostDto> searchPosts(@RequestParam("keyword") String keyword) {
        List<Post> searchResults = postService.searchPosts(keyword);
        List<PostDto> postDtos = postService.mapPostsToPostDtos(searchResults);
        return postDtos;
    }

//get all approved post by categoryId
@GetMapping("/GetAllApproved/filter")
public List<PostDto> getAllApprovedPostDtosByCategoryIdAndTagIds(
        @RequestParam(name = "postStatuses", required = false) List<String> postStatuses,
        @RequestParam(name = "minimumLikeCount", required = false) Integer minimumLikeCount,
        @RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "categoryName", required = false) String categoryName,
        @RequestParam(name = "tagNames", required = false) List<String> tagNames,
        @RequestParam(name = "startDate", required = false) LocalDateTime startDate,
        @RequestParam(name = "endDate", required = false) LocalDateTime endDate,
        @RequestParam(name = "sortBy", required = false, defaultValue = "createdDate") String sortBy,
        @RequestParam(name = "userId", required = false) Integer userId,
        @RequestParam(name = "sortDirection", required = false, defaultValue = "desc") String sortDirection) {

    List<Post> approvedPosts = postService.getAllApprovedPosts();

    if (postStatuses != null && !postStatuses.isEmpty()) {
        approvedPosts = postService.filterPostsByPostStatus(approvedPosts, postStatuses);
    }

    if (startDate != null && endDate != null) {
        approvedPosts = postService.filterPostsByDateRange(approvedPosts, startDate, endDate);
    }
    if (categoryName != null && !categoryName.trim().isEmpty()) {
        approvedPosts = postService.filterByCategoryName(approvedPosts, categoryName);
    }

    if (tagNames != null && !tagNames.isEmpty()) {
        approvedPosts = postService.filterByTagNames(approvedPosts, tagNames);
    }

    if (minimumLikeCount != null) {
        approvedPosts = postService.filterByMinimumLikeCount(approvedPosts, minimumLikeCount);
    }

    if (keyword != null && !keyword.trim().isEmpty()){
        approvedPosts = postService.GetPostsByKeyword(approvedPosts, keyword);
    }
    if (userId != null){
        approvedPosts = postService.GetPostsByUserId(approvedPosts, userId);
    }
    approvedPosts = postService.sortPosts(approvedPosts, sortBy, sortDirection);
    List<PostDto> dtos = postService.mapPostsToPostDtos(approvedPosts);
    return dtos;
}

    @GetMapping("/GetAll/filter")
    public List<PostDto> getAllPostDtosByFilterSelection(
            @RequestParam(name = "postApprovalStatuses", required = false) List<String> postApprovalStatuses,
            @RequestParam(name = "postStatuses", required = false) List<String> postStatuses,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "categoryName", required = false) String categoryName,
            @RequestParam(name = "tagNames", required = false) List<String> tagNames,
            @RequestParam(name = "minimumLikeCount", required = false) Integer minimumLikeCount,
            @RequestParam(name = "startDate", required = false) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false) LocalDateTime endDate,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdDate") String sortBy,
            @RequestParam(name = "userId", required = false) Integer userId,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "desc") String sortDirection) {

        List<Post> posts = postService.filterPostsByPostApprovalStatus(postApprovalStatuses);

        if (postStatuses != null && !postStatuses.isEmpty()) {
            posts = postService.filterPostsByPostStatus(posts, postStatuses);
        }

        if (startDate != null && endDate != null) {
            posts = postService.filterPostsByDateRange(posts, startDate, endDate);
        }
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            posts = postService.filterByCategoryName(posts, categoryName);
        }

        if (tagNames != null && !tagNames.isEmpty()) {
            posts = postService.filterByTagNames(posts, tagNames);
        }


        if (minimumLikeCount != null) {
            posts = postService.filterByMinimumLikeCount(posts, minimumLikeCount);
        }


        if (keyword != null && !keyword.trim().isEmpty()){
            posts = postService.GetPostsByKeyword(posts, keyword);
        }
        if (userId != null){
            posts = postService.GetPostsByUserId(posts, userId);
        }
        posts = postService.sortPosts(posts, sortBy, sortDirection);
        List<PostDto> dtos = postService.mapPostsToPostDtos(posts);
        return dtos;
    }

    @GetMapping("/GetAllApproved")
    public List<PostDto> getAllApprovedPostDtosByCategoryOrTag(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "categoryName", required = false) String categoryName,
            @RequestParam(name = "tagName", required = false) String tagName,
            @RequestParam(name = "startDate", required = false) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false) LocalDateTime endDate,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdDate") String sortBy,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "desc") String sortDirection,
            @RequestParam(name = "userId", required = false) Integer userId
    ) {
        List<Post> approvedPosts = postService.getAllApprovedPosts();
        if (startDate != null && endDate != null) {
            approvedPosts = postService.filterPostsByDateRange(approvedPosts, startDate, endDate);
        }
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            approvedPosts = postService.GetPostsByCategoryName(approvedPosts, categoryName);
        } else if (tagName != null) {
            approvedPosts = postService.GetPostsByTagName(approvedPosts, tagName);
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            approvedPosts = postService.GetPostsByKeyword(approvedPosts, keyword);
        }
        if (userId != null){
            approvedPosts = postService.GetPostsByUserId(approvedPosts, userId);
        }
        approvedPosts = postService.sortPosts(approvedPosts, sortBy, sortDirection);
        List<PostDto> dtos = postService.mapPostsToPostDtos(approvedPosts);
        return dtos;
    }


    @GetMapping("/GetAll")
    public List<PostDto> getAllPostDtosByCriteria(
            @RequestParam(name = "postApprovalStatuses", required = false) List<String> postApprovalStatuses,
            @RequestParam(name = "postStatuses", required = false) List<String> postStatuses,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "categoryName", required = false) String categoryName,
            @RequestParam(name = "tagNames", required = false) List<String> tagNames,
            @RequestParam(name = "startDate", required = false) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false) LocalDateTime endDate,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdDate") String sortBy,
            @RequestParam(name = "userId", required = false) Integer userId,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "desc") String sortDirection) {

        List<Post> posts = postService.filterPostsByPostApprovalStatus(postApprovalStatuses);

        if (postStatuses != null && !postStatuses.isEmpty()) {
            posts = postService.filterPostsByPostStatus(posts, postStatuses);
        }

        if (startDate != null && endDate != null) {
            posts = postService.filterPostsByDateRange(posts, startDate, endDate);
        }
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            posts = postService.filterByCategoryName(posts, categoryName);
        }

        if (tagNames != null && !tagNames.isEmpty()) {
            posts = postService.filterByTagNames(posts, tagNames);
        }
        if (keyword != null && !keyword.trim().isEmpty()){
            posts = postService.GetPostsByKeyword(posts, keyword);
        }
        if (userId != null){
            posts = postService.GetPostsByUserId(posts, userId);
        }
        posts = postService.sortPosts(posts, sortBy, sortDirection);
        List<PostDto> dtos = postService.mapPostsToPostDtos(posts);
        return dtos;
    }

    @GetMapping("/GetAll/{id}")
    public PostDto getAllById(@PathVariable Integer id) {
        Post post = postService.getPostById(id);
        PostDto dto = postService.mapPostToPostDto(post);
        return dto;
    }


    //get post detail by post id
    @GetMapping("/GetAllApproved/{id}")
    public PostDto getById(@PathVariable Integer id) {
        Post post = postService.getById(id);
        PostDto dto = postService.mapPostToPostDto(post);
        return dto;
    }

    @GetMapping("/mostVotedPost")
    public List<PostDto> findMostVotedPostInCategory(@RequestParam("categoryId") int categoryId) {
        List<Post> mostVotedPost = postService.findMostVotedPostInCategory(categoryId);
        List<PostDto> dtos = postService.mapPostsToPostDtos(mostVotedPost);
        return dtos;
    }

    // ==================================================================================================== //

    // TEACHER ROLE

    @GetMapping("/postRequest")

    public List<PostDto> getAllPostRequest(
            @RequestParam(name = "startDate", required = false) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false) LocalDateTime endDate,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdDate") String sortBy,
            @RequestParam(name = "userId", required = false) Integer userId,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "desc") String sortDirection
    ) {

        List<Post> post = postService.getPostsWithoutApprovalsAndByCurrentUserCategoryManagement();
        List<Post> posts = postService.getPostRequestsWithoutCurrentUserOwnPostRequests(post);

        if (startDate != null && endDate != null) {
            posts = postService.filterPostsByDateRange(posts, startDate, endDate);
        }

        if (userId != null){
            posts = postService.GetPostsByUserId(posts, userId);
        }

        posts = postService.sortPosts(posts, sortBy, sortDirection);

        List<PostDto> dtos = postService.mapPostsToPostDtos(posts);
        return dtos;
    }

    @GetMapping("/postRequest/{id}")
    public PostDto getRequestDetail(@PathVariable Integer id) {
        Post post = postService.getPostById(id);
        PostDto dto = postService.mapPostToPostDto(post);

        return dto;
    }

//    @PostMapping("/postRequest/approve/{id}")
//    public PostApprovalsDto approvePost(@PathVariable Integer id) {
//        PostApprovals requestPost = postService.approvePost(id);
//        PostApprovalsDto postDto = modelMapper.map(requestPost, PostApprovalsDto.class);
//
//        return postDto;
//    }

    @PostMapping("/postRequest/approve/{id}")

    public ResponseEntity<?> approvePost(@PathVariable Integer id, @RequestBody PostApprovalsRequest postApprovalsRequest) {
        try {
            // Call the service method to approve the post
            PostApprovals requestPost = postService.approvePost(id, postApprovalsRequest);

            // Map the result to DTO
            PostApprovalsDto postDto = modelMapper.map(requestPost, PostApprovalsDto.class);

            // Return a successful response with the DTO
            return ResponseEntity.ok(postDto);
        } catch (IllegalArgumentException e) {
            // Handle the case where the user is invalid
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid User");
        } catch (IllegalStateException e) {
            // Handle the case where the post cannot be approved by the user who created it
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }


//    @PostMapping("/postRequest/reject/{id}")
//    public PostApprovalsDto rejectPost(@PathVariable Integer id) {
//        PostApprovals requestPost = postService.rejectPost(id);
//        PostApprovalsDto postDto = modelMapper.map(requestPost, PostApprovalsDto.class);
//        Report report = postService.generateReportIfCountRejectedPostApprovalsForUserWithin24HoursThresholdExceeded(requestPost.getPost().getCreatedByUser().getUsId());
//        System.out.println(report);
//        return postDto;
//    }

    @PostMapping("/postRequest/reject/{id}")

    public ResponseEntity<Map<String, Object>> rejectPost(@PathVariable Integer id, @RequestBody PostApprovalsRequest postApprovalsRequest) {
        try {
            // Call the service method to reject the post
            PostApprovals requestPost = postService.rejectPost(id, postApprovalsRequest);

            // Map the result to DTO
            PostApprovalsDto postDto = modelMapper.map(requestPost, PostApprovalsDto.class);

            // Generate a report if the rejection threshold is exceeded
            Report report = postService.generateReportIfCountRejectedPostApprovalsForUserWithin24HoursThresholdExceeded(requestPost.getPost().getCreatedByUser().getUsId());


            // Create a response map with message, data (PostApprovalsDto), and report information
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Post rejected successfully");
            response.put("data", postDto);
            response.put("report", report);


            // Return a successful response with the map
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Handle the case where the user is invalid
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Invalid User"));
        } catch (IllegalStateException e) {
            // Handle the case where the post cannot be rejected by the user who created it
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "An error occurred: " + e.getMessage()));
        }
    }

    @PutMapping("/delete/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer postId) {
        postService.deletePostById(postId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    }

}
