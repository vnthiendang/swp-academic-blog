package com.swp.cms.controllers;

import com.swp.cms.dto.VoteDto;
import com.swp.cms.dto.VoteDto;
import com.swp.cms.dto.VoteDto;
import com.swp.cms.reqDto.VoteRequest;
import com.swp.entities.Vote;
import com.swp.entities.Vote;
import com.swp.entities.Vote;
import com.swp.services.VoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/vote")
public class   VoteController {
    private final VoteService voteService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/GetAll")
    public List<VoteDto> getAll() {
        List<Vote> votes = voteService.getAll();
        List<VoteDto> dtos = votes.stream()
                .map(vote -> modelMapper.map(vote, VoteDto.class))
                .collect(Collectors.toList());
        return dtos;
    }
    //get vote by postID
    @GetMapping("/getall/{postId}")
    public List<VoteDto> getAllByPostId(@PathVariable Integer postId) {
        List<Vote> votes = voteService.getAll(); // Assuming this method returns all votes
        List<VoteDto> voteDtos = votes.stream()
                .filter(vote -> Objects.equals(vote.getPost().getPostsId(), postId))
                .map(vote -> modelMapper.map(vote, VoteDto.class))
                .collect(Collectors.toList());

        return voteDtos;
    }

    @GetMapping("/{id}")
    public VoteDto getVoteById(@PathVariable Integer id) {

        Vote vote = voteService.getById(id);
        VoteDto dto = modelMapper.map(vote,VoteDto.class);
        return dto;
    }
    @PostMapping("/post")
    public VoteDto addVote(@RequestBody VoteRequest voteRequest) {
//        Vote vote = modelMapper.map(voteRequest, Vote.class);
        Vote createdVote = voteService.createVote(voteRequest);
        VoteDto voteDto = modelMapper.map(createdVote, VoteDto.class);
        return voteDto;
    }

    //Update a vote by vote id
    @PutMapping("/{voteId}")
    public VoteDto updateVote(@PathVariable Integer voteId, @RequestBody VoteRequest voteRequest) {
        Vote updatedVote = voteService.updateVote(voteId, voteRequest);
        VoteDto voteDto = modelMapper.map(updatedVote, VoteDto.class);
        return voteDto;
    }
}
