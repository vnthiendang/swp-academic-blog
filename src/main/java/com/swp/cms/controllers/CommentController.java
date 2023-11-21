package com.swp.cms.controllers;

import com.swp.cms.dto.CommentDto;
import com.swp.cms.reqDto.CommentRequest;
import com.swp.entities.Comment;
import com.swp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/comment")
public class CommentController {

    @Autowired
    private final CommentService commentService;
    @Autowired
    private ModelMapper modelMapper;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/getall")
    public List<CommentDto> getAll() {
        List<Comment> commentgories = commentService.getAll();
        List<CommentDto> commentDtos = commentgories.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());

        return commentDtos;
    }
    //Get comments by postId
    @GetMapping("/getall/{postId}")
    public List<CommentDto> getAllByPostId(@PathVariable Integer postId) {
        List<Comment> comments = commentService.getAll(); // Assuming this method returns all comments
        List<CommentDto> commentDtos = comments.stream()
                .filter(comment -> comment.getPost().getPostsId() == postId)
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());

        return commentDtos;
    }

    @GetMapping("/{id}")
    public CommentDto getCommentById(@PathVariable Integer id) {
        Comment comment = commentService.getById(id);
        CommentDto dto = modelMapper.map(comment, CommentDto.class);

        return dto;
    }

    //create a comment
    @PostMapping("/post")
    public CommentDto addComment(@RequestBody CommentRequest commentRequest) {
        Comment createdComment = commentService.createComment(commentRequest);
        CommentDto commentDto = modelMapper.map(createdComment, CommentDto.class);
        return commentDto;
    }

    //Update a comment by comment id
    @PutMapping("/{commentId}")
    public CommentDto updateComment(@PathVariable Integer commentId, @RequestBody CommentRequest commentRequest) {
        Comment updatedComment = commentService.updateComment(commentId, commentRequest);
        CommentDto commentDto = modelMapper.map(updatedComment, CommentDto.class);
        return commentDto;
    }
    @PutMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteCommentById(commentId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    }

}