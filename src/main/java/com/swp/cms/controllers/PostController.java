package com.swp.cms.controllers;

import com.swp.cms.dto.MediaDto;
import com.swp.cms.dto.PostApprovalsDto;
import com.swp.cms.dto.PostDto;
import com.swp.cms.dto.PostTagDto;
import com.swp.cms.mapper.PostMapper;
import com.swp.cms.reqDto.CommentRequest;
import com.swp.cms.reqDto.PostRequest;
import com.swp.entities.*;
import com.swp.services.PostService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public PostDto addPost(@RequestBody PostRequest postRequest) {
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
        @RequestParam(name = "categoryId", required = false) Integer categoryId,
        @RequestParam(name = "tagIds", required = false) List<Integer> tagIds) {
    List<Post> approvedPosts = postService.getAllApprovedPosts();

    if (categoryId != null) {
        approvedPosts = postService.filterByCategoryId(approvedPosts, categoryId);
    }

    if (tagIds != null && !tagIds.isEmpty()) {
        approvedPosts = postService.filterByTagIds(approvedPosts, tagIds);
    }
    if (keyword != null && !keyword.trim().isEmpty()){
        approvedPosts = postService.GetPostsByKeyword(approvedPosts, keyword);
    }
    List<PostDto> dtos = postService.mapPostsToPostDtos(approvedPosts);
    return dtos;
}

    @GetMapping("/GetAllApproved")
    public List<PostDto> getAllApprovedPostDtosByCategoryOrTag(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "categoryId", required = false) Integer categoryId,
            @RequestParam(name = "tagId", required = false) Integer tagId) {
        List<Post> approvedPosts = postService.getAllApprovedPosts();
        if (categoryId != null) {
            approvedPosts = postService.GetPostsByCategoryId(approvedPosts, categoryId);
        } else if (tagId != null) {
            approvedPosts = postService.GetPostsByTagId(approvedPosts, tagId);
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            approvedPosts = postService.GetPostsByKeyword(approvedPosts, keyword);
        }
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
