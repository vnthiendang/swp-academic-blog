package com.swp.cms.controllers;

import com.swp.cms.dto.CommentDto;
import com.swp.cms.reqDto.CommentRequest;
import com.swp.entities.Comment;
import com.swp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
//                    System.out.println(" ID: hellosfdsdddddddddddddddddddddddddddddddddddddddddddddddd");
        Comment comment = commentService.getById(id);
//                    System.out.println(" ID: " + comment.getId());
//            System.out.println("Post ID: " + comment.getCommentText());
//            System.out.println("Status: " + comment.getParentComment());
//            System.out.println("Created Date: " + comment.getPost());
//            System.out.println("Created Date: " + comment.getCreatedByUser());
//            System.out.println("sucesssssssssssssssssssssssssssDto");
        CommentDto dto = modelMapper.map(comment, CommentDto.class);
//        System.out.println("Post ID: " + dto.getCommentText());
//        System.out.println("Status: " + dto.getParentComment());
//        System.out.println("Created Date: " + dto.getPost());
//        System.out.println("Created Date: " + dto.getCreatedByUser());
            System.out.println("sucesssssssssssssssssssssssssssDto2");
        return dto;
    }

    //create a comment
    @PostMapping("/post")
    public CommentDto addComment(@RequestBody CommentRequest commentRequest) {
//        Comment comment = modelMapper.map(commentRequest, Comment.class);
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
}