package com.swp.cms.controllers;

import com.swp.cms.dto.CommentDto;
import com.swp.cms.mapper.CommentMapper;
import com.swp.entities.Comment;
import com.swp.repositories.CommentRepository;
import com.swp.services.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog/comment")

public class CommentController {
    private final CommentRepository commentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CommentMapper mapper;
    @Autowired
    private CommentService commentService;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping()
    public List<CommentDto> getAll() {
        List<Comment> comments = commentService.getAll();
        List<CommentDto> dto = mapper.fromEntityToCommentDtoList(comments);
        return dto;
    }

    @GetMapping("/{id}")
    public CommentDto getCommentById(@PathVariable Integer id) {
        Comment comment = commentService.getById(id);
        CommentDto dto = mapper.fromEntityToCommentDto(comment);
        return dto;

    }
}
