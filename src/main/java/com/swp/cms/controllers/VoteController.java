package com.swp.cms.controllers;

import com.swp.cms.dto.VoteDto;
import com.swp.cms.mapper.VoteMapper;
import com.swp.entities.Vote;
import com.swp.repositories.VoteRepository;
import com.swp.services.VoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog/vote")
public class VoteController {
    private final VoteRepository voteRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private VoteMapper mapper;
    @Autowired
    private VoteService voteService;
    public VoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @GetMapping()
    public List<VoteDto> getAll() {
        List<Vote> votes = voteService.getAll();
        List<VoteDto> dto = mapper.fromEntityToVoteDtoList(votes);
        return dto;
    }

    @GetMapping("/{id}")
    public VoteDto getVoteById(@PathVariable Integer id) {

        Vote vote = voteService.getById(id);
        VoteDto dto = mapper.fromEntityToVoteDto(vote);
        return dto;
    }
}
