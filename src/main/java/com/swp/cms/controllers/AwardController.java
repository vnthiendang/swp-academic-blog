package com.swp.cms.controllers;

import com.swp.cms.dto.AwardDto;
import com.swp.cms.dto.VoteDto;
import com.swp.cms.reqDto.AwardRequest;
import com.swp.entities.Award;
import com.swp.entities.Vote;
import com.swp.services.AwardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/award")
public class AwardController {

    @Autowired
    private final AwardService awardService;
    @Autowired
    private ModelMapper modelMapper;

    public AwardController(AwardService awardService) {
        this.awardService = awardService;
    }

    @GetMapping("/getall")
    public List<AwardDto> getAll(
            @RequestParam(name = "givenByTeacherId", required = false) Integer givenByTeacherId,
            @RequestParam(name = "startDate", required = false) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false) LocalDateTime endDate,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdDate") String sortBy,
            @RequestParam(name = "userIdReceived", required = false) Integer userIdReceived,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "desc") String sortDirection
    ) {
        List<Award> awards = awardService.getAll();

        if (givenByTeacherId != null) {
            awards = awardService.filterAwardsByGivenByTeacherId(awards, givenByTeacherId);
        }

        if (startDate != null && endDate != null) {
            awards = awardService.filterAwardsByDateRange(awards, startDate, endDate);
        }

        if (userIdReceived != null) {
            awards = awardService.filterAwardsByUserIdReceived(awards, userIdReceived);
        }

        awards = awardService.sortAwards(awards, sortBy, sortDirection);

        List<AwardDto> awardDtos = awards.stream()
                .map(award -> modelMapper.map(award, AwardDto.class))
                .collect(Collectors.toList());

        return awardDtos;
    }

    @GetMapping("/getall/{postId}")
    public List<AwardDto> getAllByPostId(@PathVariable Integer postId) {
        List<Award> awards = awardService.getAll(); // Assuming this method returns all awards
        List<AwardDto> awardDtos = awards.stream()
                .filter(award -> Objects.equals(award.getPost().getPostsId(), postId))
                .map(award -> modelMapper.map(award, AwardDto.class))
                .collect(Collectors.toList());

        return awardDtos;
    }

    @GetMapping("/{id}")
    public AwardDto getAwardById(@PathVariable Integer id) {
        Award award = awardService.getById(id);
        AwardDto dto = modelMapper.map(award, AwardDto.class);
        return dto;
    }


    //    //create a award

//    @PostMapping("/post")
//    public AwardDto addAward(@RequestBody AwardRequest awardRequest) {
//        Award createdAward = awardService.createAward(awardRequest);
//        AwardDto awardDto = modelMapper.map(createdAward, AwardDto.class);
//        return awardDto;
//    }


//    @PostMapping("/post")
//    public ResponseEntity<Object> addAward(@RequestBody AwardRequest awardRequest) {
//        int givenByUserId = awardRequest.getGivenByUserID();
//
//        if (awardService.hasTeacherGivenAwardThisWeek(givenByUserId)) {
//            String resultMessage = "You have already given an award this week. Cannot give another award.";
//            return ResponseEntity.badRequest().body(resultMessage);
//        }
//
//        String resultMessage = awardService.createOrUpdateAward(awardRequest);
//
//        if (resultMessage.contains("successfully")) {
//            // If the result message contains "successfully", it's a success message
//            if (resultMessage.contains("deleted")) {
//                return ResponseEntity.ok("Deleted successfully");
//            } else {
//                // If it's not a deletion, return the VoteDto
//                Award createdAward = awardService.getAwardByPostIdAndGivenByUserId(awardRequest.getPostID(), awardRequest.getGivenByUserID()).orElse(null);
//                if (createdAward != null) {
//                    AwardDto awardDto = modelMapper.map(createdAward, AwardDto.class);
//                    return ResponseEntity.ok(awardDto);
//                }
//            }
//        }
//
//        // If none of the conditions are met, return a bad request status
//        return ResponseEntity.badRequest().body("Failed to perform the vote operation");
//
////        if (createdAward != null) {
////            AwardDto awardDto = modelMapper.map(createdAward, AwardDto.class);
////            return ResponseEntity.ok(awardDto);
////        } else {
////            String resultMessage = "Failed to perform the award operation";
////            return ResponseEntity.badRequest().body(resultMessage);
////        }
////    } catch (IllegalArgumentException e) {
////        // Handle the specific exception thrown when invalid arguments are provided
////        return ResponseEntity.badRequest().body(e.getMessage());
////    } catch (Exception e) {
////        // Handle other general exceptions
////        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
////    }
//
//    }

    @PostMapping("/post")
    public ResponseEntity<Object> addAward(@RequestBody AwardRequest awardRequest) {
        try {
            int givenByUserId = awardRequest.getGivenByUserID();

            int postId = awardRequest.getPostID();

            // Check if the user is giving an award to their own post
            if (awardService.isPostCreatedByUser(postId, givenByUserId)) {
                String resultMessage = "You cannot give an award to your own post.";
                return ResponseEntity.badRequest().body(resultMessage);
            }

            if (awardService.hasTeacherGivenAwardThisWeek(givenByUserId)) {
                String resultMessage = "You have already given an award this week. Cannot give another award.";
                return ResponseEntity.badRequest().body(resultMessage);
            }

            String resultMessage = awardService.createOrUpdateAward(awardRequest);

            if (resultMessage.contains("successfully")) {
                return ResponseEntity.ok(resultMessage);
            } else {
                return ResponseEntity.badRequest().body(resultMessage);
            }
        } catch (IllegalArgumentException e) {
            // Provide a more detailed error message for invalid arguments
            String errorMessage = "Invalid argument: " + e.getMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        } catch (Exception e) {
            // Provide a generic error message for other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAwardById(@PathVariable Integer id) {
        try {
            // Call the service to delete the award
            awardService.deleteById(id);
            return new ResponseEntity<>("Award deleted successfully", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            // Handle case where the award with the given ID was not found
            return new ResponseEntity<>("Award not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Handle other exceptions, such as database errors
            return new ResponseEntity<>("An error occurred while deleting the Award", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    //Update a award by award id
//    @PutMapping("/{awardId}")
//    public AwardDto updateAward(@PathVariable Integer awardId, @RequestBody AwardRequest awardRequest) {
//        Award updatedAward = awardService.updateAward(awardId, awardRequest);
//        AwardDto awardDto = modelMapper.map(updatedAward, AwardDto.class);
//        return awardDto;
//    }
}