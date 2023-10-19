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


//        // Store media & tag
//        createdPost = postService.savePosts(createdPost);

        // Map the created Post entity to PostDto
        PostDto dto = modelMapper.map(post, PostDto.class);
//
//        // Map dtos
//        List<MediaDto> mediaDtoList = createdPost.getMedias().stream()
//                .map(media -> modelMapper.map(media, MediaDto.class))
//                .collect(Collectors.toList());
//        List<PostTagDto> postTagDtoList = createdPost.getTags().stream()
//                .map(postTag -> modelMapper.map(postTag, PostTagDto.class))
//                .collect(Collectors.toList());
//
//        // Set dtos
//        postDto.setMediaList(mediaDtoList);
//        postDto.setPostTagList(postTagDtoList);
        dto.setMediaList(modelMapper.map(post.getMedias(), new TypeToken<List<MediaDto>>() {
        }.getType()));
        dto.setPostTagList(modelMapper.map(post.getTags(), new TypeToken<List<PostTagDto>>() {
        }.getType()));
        return dto;
    }


    //search posts by title, detail, postgory
    @GetMapping("/search")
    public List<PostDto> searchPosts(@RequestParam("keyword") String keyword) {
        List<Post> searchResults = postService.searchPosts(keyword);

        List<PostDto> postDtos = searchResults.stream()
                .map(post -> {
                    PostDto postDto = modelMapper.map(post, PostDto.class);

                    // Map mediaList and postTagList using custom TypeMaps
                    postDto.setMediaList(modelMapper.map(post.getMedias(), new TypeToken<List<MediaDto>>() {
                    }.getType()));
                    postDto.setPostTagList(modelMapper.map(post.getTags(), new TypeToken<List<PostTagDto>>() {
                    }.getType()));

                    return postDto;
                })
                .collect(Collectors.toList());

        return postDtos;
    }

    //get approved posts
    @GetMapping("/GetAllApproved")
    public List<PostDto> getAllPostDtos() {
        List<Post> posts = postService.getAllApprovedPosts();

        List<PostDto> dtos = posts.stream()
                .map(post -> {
                    PostDto postDto = modelMapper.map(post, PostDto.class);

                    // Map mediaList and postTagList using custom TypeMaps
                    postDto.setMediaList(modelMapper.map(post.getMedias(), new TypeToken<List<MediaDto>>() {
                    }.getType()));
                    postDto.setPostTagList(modelMapper.map(post.getTags(), new TypeToken<List<PostTagDto>>() {
                    }.getType()));

                    return postDto;
                })
                .collect(Collectors.toList());

        return dtos;
    }

    //get post detail by post id
    @GetMapping("/GetAllApproved/{id}")
    public PostDto getById(@PathVariable Integer id) {
        Post post = postService.getById(id);
        PostDto dto = modelMapper.map(post, PostDto.class);
        // Map mediaList and postTagList
        dto.setMediaList(modelMapper.map(post.getMedias(), new TypeToken<List<MediaDto>>() {
        }.getType()));
        dto.setPostTagList(modelMapper.map(post.getTags(), new TypeToken<List<PostTagDto>>() {
        }.getType()));
        return dto;
    }


    // ==================================================================================================== //

    // TEACHER ROLE

    @GetMapping("/postRequest")
    public List<PostDto> getAllPostRequest() {
        List<PostDto> dtos = postMapper.fromEntityToPostDtoList(postService.getAll());
        return dtos;
    }

    @GetMapping("/postRequest/{id}")
    public PostDto getRequestDetail(@PathVariable Integer id) {
        PostDto dto = modelMapper.map(postService.getPostById(id), PostDto.class);

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
