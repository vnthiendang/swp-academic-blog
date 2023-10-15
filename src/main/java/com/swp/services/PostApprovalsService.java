package com.swp.services;

import com.swp.cms.reqDto.PostApprovalsRequest;
import com.swp.entities.Post;
import com.swp.entities.PostApprovals;
import com.swp.entities.User;
import com.swp.repositories.PostApprovalsRepository;
import com.swp.repositories.PostRepository;
import com.swp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class PostApprovalsService {
    @Autowired
    private PostApprovalsRepository postApprovalsRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public PostApprovals getById(int id) {
        return postApprovalsRepository.findById(id).orElseThrow();
    }


    public Boolean existsById(int id) {
        return postApprovalsRepository.existsById(id);
    }

    public void deleteById(int id) {
        postApprovalsRepository.deleteById(id);
    }

    public PostApprovals add(PostApprovals award) {
        return postApprovalsRepository.save(award);
    }

    public List<PostApprovals> getAll() {
        return postApprovalsRepository.findAll();
    }
    public PostApprovals createPostApprovals(PostApprovalsRequest postApprovalsRequest){
//        User viewedByUser = null;
//        Post belongedtoPost = postRepository.findById(postApprovalsRequest.getPostIdValue())
//        .orElseThrow(() -> new IllegalArgumentException("Invalid Post"));
        PostApprovals postApprovals = new PostApprovals();
        postApprovals.setPost(postRepository.findById(postApprovalsRequest.getPost()).
                orElseThrow(() -> new IllegalArgumentException("Invalid Post")));
        postApprovals.setViewedByUser(null);
        postApprovals.setCreatedDate(OffsetDateTime.now());
        postApprovals.setStatus("pending");
        return postApprovalsRepository.save(postApprovals);
    }
    public PostApprovals updatePostApprovals(Integer postApprovalsID, PostApprovalsRequest postApprovalsRequest){
        PostApprovals postApprovals = getById(postApprovalsID);
        postApprovals.setViewedByUser(userRepository.findById(postApprovalsRequest.getViewedByUser()).
                orElseThrow(() -> new IllegalArgumentException("Invalid User")));
        postApprovals.setStatus(postApprovalsRequest.getPostApprovalsStatus());
        postApprovals.setCreatedDate(OffsetDateTime.now());
        return postApprovalsRepository.save(postApprovals); // Save and return the updated post
    }

}
