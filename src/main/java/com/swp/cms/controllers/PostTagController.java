package com.swp.cms.controllers;

import com.swp.cms.dto.PostTagDto;
import com.swp.entities.PostTag;
import com.swp.services.PostTagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog/postTag")
public class PostTagController {
    private final PostTagService postTagService;

    public PostTagController(PostTagService postTagService) {
        this.postTagService = postTagService;
    }
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public PostTagDto getById(@PathVariable Integer id) {
        PostTag postTag = postTagService.getById(id);
        PostTagDto dto = modelMapper.map(postTag, PostTagDto.class);
        return dto;
    }

//    @GetMapping()
//    public List<PostTagDto> getAll() {
//        List<PostTag> postTags = postTagService.getAll();
//        List<PostTagDto> dtos = modelMapper.map(postTags, PostTagDto.class);
//        //return makeResponse(true, testingDto, "Get testing detail successful!");
//        return dtos;
//    }
}
