package com.swp.cms.controllers;

import com.swp.cms.dto.PostTagDto;
import com.swp.cms.reqDto.PostTagRequest;
import com.swp.entities.PostTag;
import com.swp.services.PostTagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/posttag")
public class PostTagController {

    @Autowired
    private final PostTagService postTagService;
    @Autowired
    private ModelMapper modelMapper;

    public PostTagController(PostTagService postTagService) {
        this.postTagService = postTagService;
    }

    @GetMapping("/getall")
    public List<PostTagDto> getAll() {
        List<PostTag> categories = postTagService.getAll();
        List<PostTagDto> postTagDtos = categories.stream()
                .map(postTag -> modelMapper.map(postTag, PostTagDto.class))
                .collect(Collectors.toList());

        return postTagDtos;
    }

    @GetMapping("/{id}")
    public PostTagDto getPostTagById(@PathVariable Integer id) {
//                    System.out.println(" ID: hellosfdsdddddddddddddddddddddddddddddddddddddddddddddddd");
        PostTag postTag = postTagService.getById(id);
//                    System.out.println(" ID: " + cate.getCateId());
//            System.out.println("Post ID: " + cate.getContent());
//            System.out.println("Status: " + cate.getParentPostTag());
//            System.out.println("Created Date: " + cate.getCreatedDate());
//            System.out.println("sucesssssssssssssssssssssssssssDto");
        PostTagDto dto = modelMapper.map(postTag, PostTagDto.class);
//                    System.out.println(" ID: " + dto.getId());
//            System.out.println("Post ID: " + dto.getContent());
//            System.out.println("Status: " + dto.getParentPostTag());
//            System.out.println("Created Date: " + dto.getCreatedDate());
//            System.out.println("sucesssssssssssssssssssssssssssDto");
        return dto;
    }

    //create a postTag
    @PostMapping("/post")
    public PostTagDto addPostTag(@RequestBody PostTagRequest postTagRequest) {
//        PostTag postTag = modelMapper.map(postTagRequest, PostTag.class);
        PostTag createdPostTag = postTagService.createPostTag(postTagRequest);
        PostTagDto postTagDto = modelMapper.map(createdPostTag, PostTagDto.class);
        return postTagDto;
    }

    //Update a postTag by postTag id
    @PutMapping("/{postTagId}")
    public PostTagDto updatePostTag(@PathVariable Integer postTagId, @RequestBody PostTagRequest postTagRequest) {
        PostTag updatedPostTag = postTagService.updatePostTag(postTagId, postTagRequest);
        PostTagDto postTagDto = modelMapper.map(updatedPostTag, PostTagDto.class);
        return postTagDto;
    }
}