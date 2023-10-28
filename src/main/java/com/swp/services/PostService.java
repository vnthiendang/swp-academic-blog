package com.swp.services;

import com.swp.cms.dto.MediaDto;
import com.swp.cms.dto.PostDto;
import com.swp.cms.dto.PostTagDto;
import com.swp.cms.reqDto.PostRequest;
import com.swp.entities.*;
import com.swp.repositories.*;
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
import java.util.HashSet;
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
    private TagRepository tagRepository;

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
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Tag"));
                PostTag postTag = new PostTag();
                postTag.setTag(tag);
                postTag.setPost(post);
                postTagList.add(postTag);
            }
            post.setPostTags(postTagList);
        }
        return postRepository.save(post);
    }


    public Post updatePost(Integer postId, PostRequest postRequest) {
        Post post = getById(postId);
        if (post == null) {
            throw new IllegalArgumentException("Post not found"); // Or handle it as needed
        }

        post.setTitle(postRequest.getTitle());
        post.setPostDetail(postRequest.getDetail());
        post.setBelongedToCategory(categoryRepository.findById(postRequest.getCategoryID())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category")));

        // Update Medias
        List<String> mediaUrls = postRequest.getMediaList();
        if (mediaUrls != null && !mediaUrls.isEmpty()) {
            // Clear existing media and replace with new ones
            post.getMedias().clear();
            List<Media> mediaList = new ArrayList<>();
            for (String mediaUrl : mediaUrls) {
                Media media = new Media();
                media.setMediaUrl(mediaUrl);
                media.setPost(post);
                mediaList.add(media);
            }
            post.setMedias(mediaList);
        } else {
            // If mediaUrls is empty, remove all existing media
            post.getMedias().clear();
        }
        // Update PostTags
        List<Integer> tagIds = postRequest.getTagList();
        if (tagIds != null && !tagIds.isEmpty()) {
            // Clear existing post tags and replace with new ones
            post.getPostTags().clear();
            List<PostTag> postTagList = new ArrayList<>();
            for (Integer tagId : tagIds) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Tag"));
                PostTag postTag = new PostTag();
                postTag.setTag(tag);
                postTag.setPost(post);
                postTagList.add(postTag);
            }
            post.setPostTags(postTagList);
        } else {
            // If tagIds is empty, remove all existing post tags
            post.getPostTags().clear();
        }

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        Integer userIds = userDetails.getUsId();

        User currentUser = userRepository.findById(userIds)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

        PostApprovals post = new PostApprovals();
        post.setPost(getPostById(id));
        post.setStatus("APPROVED");
        post.setCreatedDate(LocalDateTime.now());
        post.setViewedByUser(currentUser);
        return postApprovalsRepository.save(post);
    }

    //reject post
    public PostApprovals rejectPost(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        Integer userIds = userDetails.getUsId();

        User currentUser = userRepository.findById(userIds)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

        PostApprovals post = new PostApprovals();
        post.setPost(getPostById(id));
        post.setStatus("REJECTED");
        post.setCreatedDate(LocalDateTime.now());
        post.setViewedByUser(currentUser);
        return postApprovalsRepository.save(post);
    }

    public List<PostDto> mapPostsToPostDtos(List<Post> posts) {
        return posts.stream()
                .map(post -> {
                    PostDto postDto = modelMapper.map(post, PostDto.class);
                    return postDto;
                })
                .collect(Collectors.toList());
    }

    public PostDto mapPostToPostDto(Post post) {
        PostDto dto = modelMapper.map(post, PostDto.class);
        // Map mediaList and postTagList
        return dto;
    }

    public List<Post> filterByCategoryId(List<Post> approvedPosts, Integer categoryId) {
        if (categoryId == null) {
            return approvedPosts;
        } else {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + categoryId));
            return approvedPosts.stream()
                    .filter(post -> post.getBelongedToCategory().getCateId().equals(categoryId))
                    .collect(Collectors.toList());
        }
    }

    public List<Post> filterByTagIds(List<Post> approvedPosts, List<Integer> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return approvedPosts;
        } else {
            for (Integer tagId : tagIds) {
                if (tagRepository.findById(tagId).isEmpty()) {
                    throw new IllegalArgumentException("Tag not found with ID: " + tagId);
                }
            }

            return approvedPosts.stream()
                    .filter(post -> new HashSet<>(post.getPostTags().stream()
                            .map(PostTag::getTag)
                            .map(Tag::getId)
                            .collect(Collectors.toList()))
                            .containsAll(tagIds))
                    .collect(Collectors.toList());
        }
    }

    public List<Post> GetPostsByCategoryId(List<Post> approvedPosts, Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + categoryId));
        return approvedPosts.stream()
                .filter(post -> post.getBelongedToCategory().getCateId().equals(categoryId))
                .collect(Collectors.toList());
    }

    public List<Post> GetPostsByTagId(List<Post> approvedPosts, Integer tagId) {
        // Check if the tagId is valid
        if (tagRepository.findById(tagId).isEmpty()) {
            throw new IllegalArgumentException("Tag not found with ID: " + tagId);
        }

        return approvedPosts.stream()
                .filter(post -> post.getPostTags().stream()
                        .map(PostTag::getTag)
                        .map(Tag::getId)
                        .anyMatch(id -> id.equals(tagId))
                )
                .collect(Collectors.toList());
    }

    public List<Post> GetPostsByKeyword(List<Post> approvedPosts, String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            String lowercaseKeyword = keyword.toLowerCase().trim();
            return approvedPosts.stream()
                    .filter(post -> post.getTitle().toLowerCase().contains(lowercaseKeyword))
                    .collect(Collectors.toList());
        }
        return approvedPosts; // Return the original list if keyword is not provided
    }

    public List<Post> getPostsWithoutApprovals() {
        List<PostApprovals> postApprovals = postApprovalsRepository.findAll();

        List<Integer> postIdsWithApprovals = postApprovals
                .stream()
                .map(postApproval -> postApproval.getPost().getPostsId())
                .collect(Collectors.toList());

        return postRepository.findAll()
                .stream()
                .filter(post -> !postIdsWithApprovals.contains(post.getPostsId()))
                .collect(Collectors.toList());
    }
}
