package com.swp.cms.controllers;

import com.swp.cms.dto.MediaDto;
import com.swp.cms.dto.PostApprovalsDto;
import com.swp.cms.dto.PostDto;
import com.swp.cms.dto.PostTagDto;
import com.swp.cms.mapper.PostMapper;
import com.swp.cms.reqDto.PostRequest;
import com.swp.entities.*;
import com.swp.services.PostService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

//        // Map the created Post entity to PostDto
//        PostDto dto = modelMapper.map(post, PostDto.class);
//
//        dto.setMediaList(modelMapper.map(post.getMedias(), new TypeToken<List<MediaDto>>() {
//        }.getType()));
//        dto.setPostTagList(modelMapper.map(post.getTags(), new TypeToken<List<PostTagDto>>() {
//        }.getType()));
        return dto;
    }


    //search posts by title, detail, postgory
    @GetMapping("/search")
    public List<PostDto> searchPosts(@RequestParam("keyword") String keyword) {
        List<Post> searchResults = postService.searchPosts(keyword);
        List<PostDto> postDtos = postService.mapPostsToPostDtos(searchResults);
//        List<PostDto> postDtos = searchResults.stream()
//                .map(post -> {
//                    PostDto postDto = modelMapper.map(post, PostDto.class);
//
//                    // Map mediaList and postTagList using custom TypeMaps
//                    postDto.setMediaList(modelMapper.map(post.getMedias(), new TypeToken<List<MediaDto>>() {
//                    }.getType()));
//                    postDto.setPostTagList(modelMapper.map(post.getTags(), new TypeToken<List<PostTagDto>>() {
//                    }.getType()));
//
//                    return postDto;
//                })
//                .collect(Collectors.toList());

        return postDtos;
    }

    //get approved posts
    @GetMapping("/GetAllApproved")
    public List<PostDto> getAllPostDtos() {
        System.out.println("hellllllllllllllllllllllllllllllll1");
        List<Post> posts = postService.getAllApprovedPosts();
        System.out.println("hellllllllllllllllllllllllllllllll2");
        List<PostDto> dtos = postService.mapPostsToPostDtos(posts);
        System.out.println("hellllllllllllllllllllllllllllllll3");
//        List<PostDto> dtos = posts.stream()
//                .map(post -> {
//                    PostDto postDto = modelMapper.map(post, PostDto.class);
//
//                    // Map mediaList and postTagList using custom TypeMaps
//                    postDto.setMediaList(modelMapper.map(post.getMedias(), new TypeToken<List<MediaDto>>() {
//                    }.getType()));
//                    postDto.setPostTagList(modelMapper.map(post.getTags(), new TypeToken<List<PostTagDto>>() {
//                    }.getType()));
//
//                    return postDto;
//                })
//                .collect(Collectors.toList());

        return dtos;
    }

    //get post detail by post id
    @GetMapping("/GetAllApproved/{id}")
    public PostDto getById(@PathVariable Integer id) {
        Post post = postService.getById(id);
        PostDto dto = postService.mapPostToPostDto(post);
//        PostDto dto = modelMapper.map(post, PostDto.class);
//        // Map mediaList and postTagList
//        dto.setMediaList(modelMapper.map(post.getMedias(), new TypeToken<List<MediaDto>>() {
//        }.getType()));
//        dto.setPostTagList(modelMapper.map(post.getTags(), new TypeToken<List<PostTagDto>>() {
//        }.getType()));
        return dto;
    }

    // Filter posts by Category and Tag
//    @GetMapping("/filter")
//    public List<PostDto> filterPosts(@RequestParam(name = "categoryId", required = false) Integer categoryId,
//                                     @RequestParam(name = "tagIds", required = false) List<Integer> tagIds) {
//        System.out.println("hellllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll1");
//        List<Post> filteredPosts = postService.filterPostsByCategoryAndTag(categoryId, tagIds);
//        List<PostDto> postDtos = postService.mapPostsToPostDtos(filteredPosts);
//
//        return postDtos;
//    }
    // ==================================================================================================== //

    // TEACHER ROLE

    @GetMapping("/postRequest")
    public List<PostDto> getAllPostRequest() {
        List<Post> posts = postService.getAll();
        List<PostDto> dtos = postService.mapPostsToPostDtos(posts);
//        List<PostDto> dtos = postMapper.fromEntityToPostDtoList(postService.getAll());
        return dtos;
    }

    @GetMapping("/postRequest/{id}")
    public PostDto getRequestDetail(@PathVariable Integer id) {
        Post post = postService.getPostById(id);
        PostDto dto = postService.mapPostToPostDto(post);
//        PostDto dto = modelMapper.map(postService.getPostById(id), PostDto.class);

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
