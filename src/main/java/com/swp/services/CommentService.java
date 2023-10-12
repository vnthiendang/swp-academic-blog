package com.swp.services;

import com.swp.entities.Comment;
import com.swp.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment getById(int id) {
        return commentRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return commentRepository.existsById(id);
    }

    public void deleteById(int id) {
        commentRepository.deleteById(id);
    }

    public Comment add(Comment cmt) {
        return commentRepository.save(cmt);
    }

    public List<Comment> getAll() {
        return commentRepository.findAll();
    }
}
