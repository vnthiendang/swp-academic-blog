package com.swp.services;

import com.swp.cms.dto.MediaDto;
import com.swp.cms.dto.PostDto;
import com.swp.cms.dto.PostTagDto;
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
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private ModelMapper modelMapper;
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

    public Post getPostById(Integer id) {
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

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post createPost(PostRequest postRequest) {
        Post post = new Post();
        post.setCreatedByUser(userRepository.findById(postRequest.getUserID())
                .orElseThrow(() -> new IllegalArgumentException("Invalid User")));
        post.setBelongedToCategory(categoryRepository.findById(postRequest.getCategoryID())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category")));
        post.setTitle(postRequest.getTitle());
        post.setPostDetail(postRequest.getDetail());
        post.setCreatedTime(LocalDateTime.now());

        List<String> mediaUrls = postRequest.getMediaList(); // Change to a list of media URLs
        if (mediaUrls != null && !mediaUrls.isEmpty()) {
            List<Media> mediaList = new ArrayList<>();
            for (String mediaUrl : mediaUrls) {
                Media media = new Media();
                media.setMediaUrl(mediaUrl);
                media.setPost(post);
                mediaList.add(media);
            }
            post.setMedias(mediaList);
        }

        List<Integer> tagIds = postRequest.getTagList(); // Change to a list of tag IDs
        if (tagIds != null && !tagIds.isEmpty()) {
            List<PostTag> postTagList = new ArrayList<>();
            for (Integer tagId : tagIds) {
                Tag tag = new Tag();
                tag.setId(tagId);
                PostTag postTag = new PostTag();
                postTag.setTag(tag);
                postTag.setPost(post);
                postTagList.add(postTag);
            }
            post.setTags(postTagList);
        }
        return postRepository.save(post);
    }


    public Post updatePost(Integer postId, PostRequest postRequest) {
        Post post = getById(postId);
        post.setTitle(postRequest.getTitle());
        post.setPostDetail(postRequest.getDetail());
        post.setBelongedToCategory(categoryRepository.findById(postRequest.getCategoryID())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category")));

        return postRepository.save(post); // Save and return the updated post
    }

    //DISPLAY APPROVED POSTS
    public List<Post> searchPosts(String keyword) {
        return postRepository.searchApprovedPosts(keyword);
    }

    //SAVE MEDIA AND TAG
//    @Transactional
//    public Post savePosts(Post createdPost) {
//
//        List<Media> mediaList = createdPost.getMedias();
//        if (mediaList != null && !mediaList.isEmpty()) {
//            for (Media media : mediaList) {
//                media.setPost(createdPost);
//                entityManager.persist(media);
//            }
//        }
//
//        List<PostTag> tagList = createdPost.getTags();
//        if (tagList != null && !tagList.isEmpty()) {
//            for (PostTag tag : tagList) {
//                tag.setPost(createdPost);
//                entityManager.persist(tag);
//            }
//        }
//
//        return createdPost;
//    }

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

    public List<PostDto> mapPostsToPostDtos(List<Post> posts) {
        return posts.stream()
                .map(post -> {
                    PostDto postDto = modelMapper.map(post, PostDto.class);
                    postDto.setMediaList(modelMapper.map(post.getMedias(), new TypeToken<List<MediaDto>>() {
                    }.getType()));
                    postDto.setPostTagList(modelMapper.map(post.getTags(), new TypeToken<List<PostTagDto>>() {
                    }.getType()));
                    return postDto;
                })
                .collect(Collectors.toList());
    }

    public PostDto mapPostToPostDto(Post post) {
        PostDto dto = modelMapper.map(post, PostDto.class);
        // Map mediaList and postTagList
        dto.setMediaList(modelMapper.map(post.getMedias(), new TypeToken<List<MediaDto>>() {
        }.getType()));
        dto.setPostTagList(modelMapper.map(post.getTags(), new TypeToken<List<PostTagDto>>() {
        }.getType()));
        return dto;
    }

//    public List<Post> filterPostsByCategoryAndTag(Integer categoryId, List<Integer> tagIds) {
//        System.out.println("hellllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll2");
//        return postRepository.findAllByCategoryAndTags(categoryId, tagIds);
//    }

}
