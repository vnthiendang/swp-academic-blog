package com.swp.cms.controllers;

import com.swp.cms.dto.PostApprovalsDto;
import com.swp.cms.dto.PostDto;
import com.swp.cms.mapper.PostMapper;
import com.swp.cms.reqDto.PostRequest;
import com.swp.entities.Post;
import com.swp.entities.PostApprovals;
import com.swp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/blog/post")
public class PostController {
    @Autowired
    private final PostService postService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostMapper postMapper;

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
    public PostDto updatePost(@PathVariable Integer id, @RequestBody PostRequest postRequest){
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
        @RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "categoryName", required = false) String categoryName,
        @RequestParam(name = "tagNames", required = false) List<String> tagNames,
        @RequestParam(name = "startDate", required = false) LocalDateTime startDate,
        @RequestParam(name = "endDate", required = false) LocalDateTime endDate,
        @RequestParam(name = "sortBy", required = false, defaultValue = "createdDate") String sortBy,
        @RequestParam(name = "userId", required = false) Integer userId,
        @RequestParam(name = "sortDirection", required = false, defaultValue = "desc") String sortDirection) {

    List<Post> approvedPosts = postService.getAllApprovedPosts();
    if (startDate != null && endDate != null) {
        approvedPosts = postService.filterPostsByDateRange(approvedPosts, startDate, endDate);
    }
    if (categoryName != null && !categoryName.trim().isEmpty()) {
        approvedPosts = postService.filterByCategoryName(approvedPosts, categoryName);
    }

    if (tagNames != null && !tagNames.isEmpty()) {
        approvedPosts = postService.filterByTagNames(approvedPosts, tagNames);
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
    public List<PostDto> getAllPostRequest() {
        List<Post> posts = postService.getPostsWithoutApprovals();
        List<PostDto> dtos = postService.mapPostsToPostDtos(posts);
        return dtos;
    }

    @GetMapping("/postRequest/{id}")
    public PostDto getRequestDetail(@PathVariable Integer id) {
        Post post = postService.getPostById(id);
        PostDto dto = postService.mapPostToPostDto(post);

        return dto;
    }

    @PostMapping("/postRequest/approve/{id}")
    public PostApprovalsDto approvePost(@PathVariable Integer id) {
        PostApprovals requestPost = postService.approvePost(id);

        PostApprovalsDto postDto = modelMapper.map(requestPost, PostApprovalsDto.class);

        return postDto;
    }

    @PostMapping("/postRequest/reject/{id}")
    public PostApprovalsDto rejectPost(@PathVariable Integer id) {
        PostApprovals requestPost = postService.rejectPost(id);

        PostApprovalsDto postDto = modelMapper.map(requestPost, PostApprovalsDto.class);

        return postDto;
    }

}
