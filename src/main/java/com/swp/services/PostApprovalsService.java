package com.swp.services;

import com.swp.entities.PostApprovals;
import com.swp.repositories.PostApprovalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostApprovalsService {
    @Autowired
    private PostApprovalsRepository postApprovalsRepository;

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
}
