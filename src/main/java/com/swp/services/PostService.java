package com.swp.services;

import com.swp.entities.Post;
import com.swp.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post getById(int id) {
        return postRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return postRepository.existsById(id);
    }

    public void deleteById(int id) {
        postRepository.deleteById(id);
    }

    public Post add(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }
}
