package com.swp.services;

import com.swp.cms.reqDto.CommentRequest;
import com.swp.cms.reqDto.CommentRequest;
import com.swp.entities.Comment;
import com.swp.entities.Comment;
import com.swp.repositories.CommentRepository;
import com.swp.repositories.CommentRepository;
import com.swp.repositories.PostRepository;
import com.swp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public Comment getById(int id) {
        return commentRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return commentRepository.existsById(id);
    }

    public void deleteById(int id) {
        commentRepository.deleteById(id);
    }

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public Comment createComment(CommentRequest commentRequest){
        Comment comment = new Comment();
        comment.setCommentText(commentRequest.getCommentText());
        comment.setPost(postRepository.findById(commentRequest.getBelongedToPostID()).
                orElseThrow(() -> new IllegalArgumentException("Invalid Post")));
        comment.setCreatedByUser(userRepository.findById(commentRequest.getCreatedByUserID()).
                orElseThrow(() -> new IllegalArgumentException("Invalid User")));
        if(commentRequest.getParentCommentID() != null) {
            comment.setParentComment(commentRepository.findById(commentRequest.getParentCommentID()).
                    orElseThrow(() -> new IllegalArgumentException("Invalid Comment")));
        } else {
            comment.setParentComment(null);
        }
        return commentRepository.save(comment);
    }
    public Comment updateComment(Integer commentID, CommentRequest commentRequest){
        Comment comment = getById(commentID);
        comment.setCommentText(commentRequest.getCommentText());
        return commentRepository.save(comment);
    }
}
