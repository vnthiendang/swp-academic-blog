package com.swp.cms.controllers;

import com.swp.cms.dto.VoteDto;
import com.swp.entities.Vote;
import com.swp.services.VoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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


    @GetMapping("/{id}")
    public VoteDto getVoteById(@PathVariable Integer id) {

        Vote vote = voteService.getById(id);
        VoteDto dto = modelMapper.map(vote,VoteDto.class);
        return dto;
    }
}
