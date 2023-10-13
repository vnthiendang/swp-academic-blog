package com.swp.cms.controllers;

import com.swp.cms.dto.MediaDto;
import com.swp.cms.dto.PostDto;
import com.swp.cms.dto.PostTagDto;
import com.swp.cms.mapper.PostMapper;
import com.swp.cms.reqDto.PostRequest;
import com.swp.entities.Media;
import com.swp.entities.Post;
import com.swp.entities.PostTag;
import com.swp.entities.Tag;
import com.swp.services.MediaService;
import com.swp.services.PostService;
import com.swp.services.PostTagService;
import com.swp.services.TagService;
import org.modelmapper.ModelMapper;
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

    //create post (upload mediaUrl & tag)
    @PostMapping()
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
    @GetMapping
    public List<PostDto> getAllPostDtos() {
        List<Post> posts = postService.getAllApprovedPosts();
        List<PostDto> dtos = postMapper.fromEntityToPostDtoList(posts);
        return dtos;
    }

    //get post detail by post id
//    @GetMapping("/{id}")
//    public PostDto getById(@PathVariable Integer id) {
//        Post post = postService.getById(id);
//        PostDto dto = modelMapper.map(post, PostDto.class);
//
//        List<PostTag> postTagList = postTagService.getAll();
//        for(PostTag postTag : postTagList){
//            if(postTag.getPost().getPostsId() == id){
//                PostTagDto postTagDto = modelMapper.map(postTag, PostTagDto.class);
//                dto.setPostTag(postTagDto);
//            }
//        }
//
//        List<Media> mediaList = mediaService.getAll();
//        for(Media media : mediaList){
//            if(media.getPost().getPostsId() == id){
//                MediaDto mediaDto = modelMapper.map(media, MediaDto.class);
//                dto.setMedia(mediaDto);
//            }
//        }
//        return dto;
//    }

    //edit post
//    @PutMapping("/{postId}")
//    public PostDto updatePost(@PathVariable Integer postId, @RequestBody PostRequest postRequest) {
//        Post updatedPost = postService.updatePost(postId, postRequest);
//        PostDto postDto = modelMapper.map(updatedPost, PostDto.class);
//
//        String mediaUrl = postRequest.getMedia();
//        if (mediaUrl != null) {
//            Media media = new Media();
//            media.setMediaUrl(mediaUrl);
//            media.setPost(updatedPost);
////            mediaService.add(media);
//            MediaDto mediaDto = modelMapper.map(media, MediaDto.class);
//            postDto.setMedia(mediaDto);
//        }
//
//        Integer tagId = postRequest.getTag();
//        if(tagId != null){
//            PostTag postTag = new PostTag();
//            Tag tag = tagService.getById(tagId);
//            tag.setId(tagId);
//            postTag.setTag(tag);
//            postTag.setPost(updatedPost);
//            postTagService.addPostTag(postTag);
//            PostTagDto postTagDto = modelMapper.map(postTag, PostTagDto.class);
//            postDto.setPostTag(postTagDto);
//        }
//
//        return postDto;
//    }

}
