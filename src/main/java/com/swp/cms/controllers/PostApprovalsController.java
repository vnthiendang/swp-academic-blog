package com.swp.cms.controllers;

import com.swp.cms.dto.*;
import com.swp.cms.reqDto.PostApprovalsRequest;
import com.swp.entities.*;
import com.swp.services.*;
import jakarta.annotation.security.RolesAllowed;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/postapproval")
public class PostApprovalsController {

    private final PostApprovalsService postApprovalsService;
    @Autowired
    private ModelMapper modelMapper;


    public PostApprovalsController(PostApprovalsService postApprovalsService) {
        this.postApprovalsService = postApprovalsService;
    }

    //    @RolesAllowed("ADMIN")
//    @GetMapping("/getall")
//    public List<PostApprovalsDto> getAll() {
//        // Check authentication
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        List<PostApprovals> res = postApprovalsService.getAll();
//        List<PostApprovalsDto> postApprovalsDtos = mapper.fromEntityToPostApprovalsDtoList(res);
//        return postApprovalsDtos;
//
//    }
    @RolesAllowed("ADMIN")
    @GetMapping("/getall")
    public List<PostApprovalsDto> getAll() {
        // Check authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<PostApprovals> res = postApprovalsService.getAll();
        List<PostApprovalsDto> postApprovalsDtos = res.stream()
                .map(PostApprovals -> modelMapper.map(PostApprovals, PostApprovalsDto.class))
                .collect(Collectors.toList());
        return postApprovalsDtos;

    }

    //Get post_approvals by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
//            System.out.println(" ID: hellosfdsdddddddddddddddddddddddddddddddddddddddddddddddd");
            PostApprovals postApprovals = postApprovalsService.getById(id);
//            System.out.println(" ID: " + postApprovals.getId());
//            System.out.println("Post ID: " + postApprovals.getPost());
//            System.out.println("Status: " + postApprovals.getStatus());
//            System.out.println("Created Date: " + postApprovals.getCreatedDate());
//            System.out.println("Viewed By User: " + postApprovals.getViewedByUser());
//            System.out.println("sucesssssssssssssssssssssssssssDto");
            PostApprovalsDto dto = modelMapper.map(postApprovals, PostApprovalsDto.class);
//            System.out.println(" ID: " + dto.getId());
//            System.out.println("Post ID: " + dto.getPost());
//            System.out.println("Status: " + dto.getStatus());
//            System.out.println("Created Date: " + dto.getCreatedDate());
//            System.out.println("Viewed By User: " + dto.getViewedByUser());
//            System.out.println("sucesssssssssssssssssssssssssssDto");
            return ResponseEntity.ok(dto);
        } catch (NoSuchElementException ex) {
            // PostApprovals not found with the given ID
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            // Handle other exceptions as needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    //Post a post_approvals
    @PostMapping("/post")
    public PostApprovalsDto addPostApprovals(@RequestBody PostApprovalsRequest postApprovalsRequest) {
//        PostApprovals postApprovals = modelMapper.map(postApprovalsRequest, PostApprovals.class);
        PostApprovals createdPostApprovals = postApprovalsService.createPostApprovals(postApprovalsRequest);
        PostApprovalsDto postApprovalsDto = modelMapper.map(createdPostApprovals, PostApprovalsDto.class);
        return postApprovalsDto;
    }

    //Update a post_approvals by postApprovalsId
    @PutMapping("/{postApprovalsId}")
    public PostApprovalsDto updatePostApprovals(@PathVariable Integer postApprovalsId, @RequestBody PostApprovalsRequest postApprovalsRequest) {
        PostApprovals updatedPostApprovals = postApprovalsService.updatePostApprovals(postApprovalsId, postApprovalsRequest);
        PostApprovalsDto postApprovalsDto = modelMapper.map(updatedPostApprovals, PostApprovalsDto.class);
        return postApprovalsDto;
    }

// De test data
//    public PostApprovalsDto getByIDD(){
//
//        PostApprovals postApprovals = postApprovalsService.getById(1);
//        if(postApprovals!= null){
//            System.out.println(" ID: " + postApprovals.getId());
//            System.out.println("Post ID: " + postApprovals.getPost());
//            System.out.println("Status: " + postApprovals.getStatus());
//            System.out.println("Created Date: " + postApprovals.getCreatedDate());
//            System.out.println("Viewed By User: " + postApprovals.getViewedByUser());
//            System.out.println("sucesssssssssssssssssssssssssssDto");
//        }
//        PostApprovalsDto dto = modelMapper.map(postApprovals, PostApprovalsDto.class);
//
//        if(dto != null) {
//            //display property value of dto
//            System.out.println(" ID: " + dto.getId());
//            System.out.println("Post ID: " + dto.getPost());
//            System.out.println("Status: " + dto.getStatus());
//            System.out.println("Created Date: " + dto.getCreatedDate());
//            System.out.println("Viewed By User: " + dto.getViewedByUser());
//            System.out.println("sucesssssssssssssssssssssssssssDto");
//            return dto;
//        }else{
//            System.out.println("failsssssssssssssssslllllllllllllllllllllllllllll");
//            return null;
//        }
//
//    }
}
