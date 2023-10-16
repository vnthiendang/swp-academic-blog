package com.swp.services;

import com.swp.cms.reqDto.PostTagRequest;
import com.swp.cms.reqDto.PostTagRequest;
import com.swp.entities.PostTag;
import com.swp.entities.PostTag;
import com.swp.repositories.PostRepository;
import com.swp.repositories.PostTagRepository;
import com.swp.repositories.TagRepository;
import com.swp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class PostTagService {
    @Autowired
    private PostTagRepository postTagRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagRepository tagRepository;

    public PostTag getById(Integer id) {
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

    public PostTag createPostTag(PostTagRequest postTagRequest){
        PostTag postTag = new PostTag();
        postTag.setPost(postRepository.findById(postTagRequest.getPost()).
                orElseThrow(() -> new IllegalArgumentException("Invalid Post")));
        postTag.setTag(tagRepository.findById(postTagRequest.getTag()).
                orElseThrow(() -> new IllegalArgumentException("Invalid Tag")));
        return postTagRepository.save(postTag);
    }
    public PostTag updatePostTag(Integer postTagID, PostTagRequest postTagRequest){
        PostTag postTag = getById(postTagID);
        postTag.setPost(postRepository.findById(postTagRequest.getPost()).
                orElseThrow(() -> new IllegalArgumentException("Invalid Post")));
        postTag.setTag(tagRepository.findById(postTagRequest.getTag()).
                orElseThrow(() -> new IllegalArgumentException("Invalid Tag")));
        return postTagRepository.save(postTag); // Save and return the updated post
    }
}
