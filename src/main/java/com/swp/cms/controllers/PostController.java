package com.swp.cms.controllers;

import com.swp.cms.dto.PostDto;
import com.swp.cms.mapper.PostMapper;
import com.swp.entities.Post;
import com.swp.repositories.PostRepository;
import com.swp.services.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/blog/post")
public class PostController {
    private final PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostMapper mapper;
    @Autowired
    private PostService postService;
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping()
    public List<PostDto> getAll() {
        List<Post> posts = postService.getAll();
        List<PostDto> dto = mapper.fromEntityToPostDtoList(posts);
        return dto;
    }

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable Integer id) {
        Post post = postRepository.getOne(id);
        PostDto dto = mapper.fromEntityToPostDto(post);
        return dto;
    }
}
