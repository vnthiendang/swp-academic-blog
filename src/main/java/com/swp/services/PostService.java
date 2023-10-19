package com.swp.services;

import com.swp.cms.reqDto.PostRequest;
import com.swp.entities.*;
import com.swp.repositories.CategoryRepository;
import com.swp.repositories.PostApprovalsRepository;
import com.swp.repositories.PostRepository;
import com.swp.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostApprovalsRepository postApprovalsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @PersistenceContext
    private EntityManager entityManager;
    private Integer userId;
    @PostConstruct
    public void initialize() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User userDetails = (User) authentication.getPrincipal();
            userId = userDetails.getUsId();
        }
    }
    public Post getById(Integer id) {
        List<Post> approvedPosts = postRepository.findAllApprovedPosts();
        Optional<Post> optionalPost = approvedPosts.stream()
                .filter(post -> post.getPostsId().equals(id))
                .findFirst();

        return optionalPost.orElse(null);
    }

    public Post getPostById(Integer id){
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

    public List<Post> getAll(){
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

    //SAVE MEDIA AND TAG
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

    //approve post
    public PostApprovals approvePost(Integer id) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

        PostApprovals post = new PostApprovals();
        post.setPost(getById(id));
        post.setStatus("APPROVE");
        post.setCreatedDate(LocalDateTime.now());
        post.setViewedByUser(currentUser);
        return postApprovalsRepository.save(post);
    }

    //reject post
    public PostApprovals rejectPost(Integer id) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

        PostApprovals post = new PostApprovals();
        post.setPost(getById(id));
        post.setStatus("REJECTED");
        post.setCreatedDate(LocalDateTime.now());
        post.setViewedByUser(currentUser);
        return postApprovalsRepository.save(post);
    }
}
