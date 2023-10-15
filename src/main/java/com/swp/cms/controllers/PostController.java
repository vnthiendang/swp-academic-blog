package com.swp.cms.controllers;

import com.swp.cms.dto.MediaDto;
import com.swp.cms.dto.PostApprovalsDto;
import com.swp.cms.dto.PostDto;
import com.swp.cms.dto.PostTagDto;
import com.swp.cms.reqDto.PostRequest;
import com.swp.entities.*;
import com.swp.services.MediaService;
import com.swp.services.PostService;
import com.swp.services.PostTagService;
import com.swp.services.TagService;
import jakarta.annotation.security.RolesAllowed;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/post")
public class PostController {
    private final PostService postService;
    private final MediaService mediaService;
    private final TagService tagService;
    private final PostTagService postTagService;
    @Autowired
    private ModelMapper modelMapper;

    public PostController(PostService postService, MediaService mediaService, TagService tagService, PostTagService postTagService) {
        this.postService = postService;
        this.mediaService = mediaService;
        this.tagService = tagService;
        this.postTagService = postTagService;
    }

    //create post (upload mediaUrl & tag)
    @PostMapping()
    public PostDto addPost(@RequestBody PostRequest postRequest) {
        Post post = modelMapper.map(postRequest, Post.class);
        Post createdPost = postService.createPost(postRequest);
        PostDto postDto = modelMapper.map(createdPost, PostDto.class);

        String mediaUrl = postRequest.getMedia();
        if (mediaUrl != null) {
                Media media = new Media();
                media.setMediaUrl(mediaUrl);
                media.setPost(createdPost);
                mediaService.add(media);
            MediaDto mediaDto = modelMapper.map(media, MediaDto.class);
            postDto.setMedia(mediaDto);
        }


        PostTag postTag = new PostTag();
        Tag tag = new Tag();
        tag.setId(postRequest.getTag());
        postTag.setTag(tag);
        postTag.setPost(createdPost);
        postTagService.addPostTag(postTag);

        PostTagDto postTagDto = modelMapper.map(postTag, PostTagDto.class);
        postDto.setPostTag(postTagDto);

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

    //get post detail by post id
    @GetMapping("/{id}")
    public PostDto getById(@PathVariable Integer id) {
        Post post = postService.getById(id);
        PostDto dto = modelMapper.map(post, PostDto.class);

        List<PostTag> postTagList = postTagService.getAll();
        for(PostTag postTag : postTagList){
            if(postTag.getPost().getPostsId() == id){
                PostTagDto postTagDto = modelMapper.map(postTag, PostTagDto.class);
                dto.setPostTag(postTagDto);
            }
        }

        List<Media> mediaList = mediaService.getAll();
        for(Media media : mediaList){
            if(media.getPost().getPostsId() == id){
                MediaDto mediaDto = modelMapper.map(media, MediaDto.class);
                dto.setMedia(mediaDto);
            }
        }
        return dto;
    }

    //edit post
    @PutMapping("/{postId}")
    public PostDto updatePost(@PathVariable Integer postId, @RequestBody PostRequest postRequest) {
        Post updatedPost = postService.updatePost(postId, postRequest);
        PostDto postDto = modelMapper.map(updatedPost, PostDto.class);

        String mediaUrl = postRequest.getMedia();
        if (mediaUrl != null) {
            Media media = new Media();
            media.setMediaUrl(mediaUrl);
            media.setPost(updatedPost);
//            mediaService.add(media);
            MediaDto mediaDto = modelMapper.map(media, MediaDto.class);
            postDto.setMedia(mediaDto);
        }

        Integer tagId = postRequest.getTag();
        if(tagId != null){
            PostTag postTag = new PostTag();
            Tag tag = tagService.getById(tagId);
            tag.setId(tagId);
            postTag.setTag(tag);
            postTag.setPost(updatedPost);
            postTagService.addPostTag(postTag);
            PostTagDto postTagDto = modelMapper.map(postTag, PostTagDto.class);
            postDto.setPostTag(postTagDto);
        }

        return postDto;
    }


    @GetMapping("/postthatApproved")
    public List<PostDto> getAllApprovedPost() {
        List<Post> approvedposts = postService.getAllApprovedPosts();
        List<PostDto> postDtos = approvedposts.stream()
                .map(Post -> modelMapper.map(Post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;

    }
//    private Media saveMedia(MultipartFile mediaFile, Post post) throws IOException {
//        String fileUrl = mediaFile.getOriginalFilename();
////        String contentType = mediaFile.getContentType();
//        byte[] fileData = mediaFile.getBytes();
//
//        // Save the media data to the media table
//        Media media = new Media();
//        media.setMediaUrl(fileUrl);
//        media.setPost(post);
//        mediaService.add(media);
//
//        return media;
//    }
}
