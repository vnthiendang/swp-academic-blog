package com.swp.services;

import com.swp.entities.PostTag;
import com.swp.repositories.PostTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTagService {
    @Autowired
    private PostTagRepository postTagRepository;

    public PostTag getById(int id) {
        return postTagRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return postTagRepository.existsById(id);
    }

    public void deleteById(int id) {
        postTagRepository.deleteById(id);
    }

    public PostTag addPostTag(PostTag postTag) {
        return postTagRepository.save(postTag);
    }

    public List<PostTag> getAll() {
        return postTagRepository.findAll();
    }
}
