package com.swp.services;

import com.swp.cms.reqDto.PostRequest;
import com.swp.entities.*;
import com.swp.repositories.CategoryRepository;
import com.swp.repositories.PostRepository;
import com.swp.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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

    public List<Post> getAllApprovedPosts() {
        return postRepository.findAllApprovedPosts();
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

    public Post updatePost(Integer postId, PostRequest postRequest) {
        Post post = getById(postId);
        post.setTitle(postRequest.getTitle());
        post.setPostDetail(postRequest.getDetail());
        post.setBelongedToCategory(categoryRepository.findById(postRequest.getCategoryIdValue())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category")));

        return postRepository.save(post); // Save and return the updated post
    }

    //DISPLAY APPROVED POSTS
    public List<Post> searchPosts(String keyword) {
        return postRepository.searchApprovedPosts(keyword);
    }

    //save media and tag
    @Transactional
    public Post savePosts(Post createdPost) {

        List<Media> mediaList = createdPost.getMedias();
        if (mediaList != null && !mediaList.isEmpty()) {
            for (Media media : mediaList) {
                media.setPost(createdPost);
                entityManager.persist(media);
            }
        }

        List<PostTag> tagList = createdPost.getTags();
        if (tagList != null && !tagList.isEmpty()) {
            for (PostTag tag : tagList) {
                tag.setPost(createdPost);
                entityManager.persist(tag);
            }
        }

        return createdPost;
    }
}
