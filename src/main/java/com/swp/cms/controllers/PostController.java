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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
        Post createdPost = postService.createPost(postRequest);

        String mediaUrl = postRequest.getMedia();
        if (mediaUrl != null) {
            Media media = new Media();
            media.setMediaUrl(mediaUrl);
            media.setPost(createdPost);
            createdPost.getMedias().add(media);
        }

        Integer tagId = postRequest.getTag();
        if (tagId != null) {
            Tag tag = new Tag();
            tag.setId(tagId);
            PostTag postTag = new PostTag();
            postTag.setTag(tag);
            postTag.setPost(createdPost);
            createdPost.getTags().add(postTag);
        }

        //store media & tag
        createdPost = postService.savePosts(createdPost);

        // Map the created Post entity to PostDto
        PostDto postDto = modelMapper.map(createdPost, PostDto.class);

        // Map dtos
        List<MediaDto> mediaDtoList = createdPost.getMedias().stream()
                .map(media -> modelMapper.map(media, MediaDto.class))
                .collect(Collectors.toList());
        List<PostTagDto> postTagDtoList = createdPost.getTags().stream()
                .map(postTag -> modelMapper.map(postTag, PostTagDto.class))
                .collect(Collectors.toList());

        // Set dtos
        postDto.setMedia(mediaDtoList.isEmpty() ? null : mediaDtoList.get(0));
        postDto.setPostTag(postTagDtoList.isEmpty() ? null : postTagDtoList.get(0));

        return postDto;
    }

    //search posts by title, detail, category
    @GetMapping("/search")
    public List<PostDto> searchPosts(@RequestParam("keyword") String keyword) {
        List<Post> searchResults = postService.searchPosts(keyword);
        List<PostDto> postDtos = searchResults.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }

    //get approved posts
    @GetMapping("/GetAllApproved")
    public List<PostDto> getAllPostDtos() {
        List<Post> posts = postService.getAllApprovedPosts();
        List<PostDto> dtos = postMapper.fromEntityToPostDtoList(posts);
        return dtos;
    }

    //get post detail by post id
    @GetMapping("/GetAllApproved/{id}")
    public PostDto getById(@PathVariable Integer id) {
        Post post = postService.getById(id);
        PostDto dto = modelMapper.map(post, PostDto.class);
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
