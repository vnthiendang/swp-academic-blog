package com.swp.services;

import com.swp.cms.dto.PostDto;
import com.swp.cms.reqDto.PostRequest;
import com.swp.entities.*;
import com.swp.repositories.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Post getById(Integer id) {
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

    public Post createPost(PostRequest request) {
        User createdByUser = userRepository.findById(request.getUserIdValue())
                .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

        Category belongedToCategory = categoryRepository.findById(request.getCategoryIdValue())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setPostDetail(request.getDetail());
        post.setCreatedTime(LocalDateTime.now());
        post.setCreatedByUser(createdByUser);
        post.setBelongedToCategory(belongedToCategory);

        return postRepository.save(post);
    }

    public List<Post> searchPosts(String keyword) {
        return postRepository.search(keyword);
    }


}
