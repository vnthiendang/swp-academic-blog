package com.swp.cms.controllers;

import com.swp.cms.dto.PostTagDto;
import com.swp.cms.mapper.PostTagMapper;
import com.swp.entities.PostTag;
import com.swp.repositories.PostTagRepository;
import com.swp.services.PostTagService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog/post")
public class PostTagController {
    private final PostTagRepository postTagRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostTagMapper mapper;
    @Autowired
    private PostTagService postTagService;
    public PostTagController(PostTagRepository postTagRepository) {
        this.postTagRepository = postTagRepository;
    }
    @GetMapping()
    public List<PostTagDto> getAll() {
        List<PostTag> postTags = postTagService.getAll();
        List<PostTagDto> dto = mapper.fromEntityToPostTagDtoList(postTags);
        return dto;
    }

    @GetMapping("/{id}")
    public PostTagDto getPostTagById(@PathVariable Integer id) {
        PostTag postTag = postTagService.getById(id);
        PostTagDto dto = mapper.fromEntityToPostTagDto(postTag);
        return dto;
    }

}
