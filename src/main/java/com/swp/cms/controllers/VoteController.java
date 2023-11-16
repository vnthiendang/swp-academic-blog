package com.swp.cms.controllers;

import com.swp.cms.dto.VoteDto;
import com.swp.cms.reqDto.VoteRequest;
import com.swp.entities.Vote;
import com.swp.services.VoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

//    @PostMapping("/post")
//    public VoteDto addVote(@RequestBody VoteRequest voteRequest) {
//        Vote createdVote = voteService.createOrUpdateVote(voteRequest);
//        VoteDto voteDto = modelMapper.map(createdVote, VoteDto.class);
//        return voteDto;
//    }

    @PostMapping("/post")
    public ResponseEntity<Object> addVote(@RequestBody VoteRequest voteRequest) {
        String resultMessage = voteService.createOrUpdateVote(voteRequest);

        if (resultMessage.contains("successfully")) {
            // If the result message contains "successfully", it's a success message
            if (resultMessage.contains("deleted")) {
                return ResponseEntity.ok("Unvoted successfully");
            } else {
                // If it's not a deletion, return the VoteDto
                Vote createdVote = voteService.getVoteByUserIdAndPostId(voteRequest.getUserID(), voteRequest.getPostID()).orElse(null);
                if (createdVote != null) {
                    VoteDto voteDto = modelMapper.map(createdVote, VoteDto.class);
                    return ResponseEntity.ok(voteDto);
                }
            }
        }

        // If none of the conditions are met, return a bad request status
        return ResponseEntity.badRequest().body("Failed to perform the vote operation");
    }

//    //Update a vote by vote id
//    @PutMapping("/{voteId}")
//    public VoteDto updateVote(@PathVariable Integer voteId, @RequestBody VoteRequest voteRequest) {
//        Vote updatedVote = voteService.updateVote(voteId, voteRequest);
//        VoteDto voteDto = modelMapper.map(updatedVote, VoteDto.class);
//        return voteDto;
//    }
}
