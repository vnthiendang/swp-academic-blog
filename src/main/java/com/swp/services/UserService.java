package com.swp.services;

import com.swp.cms.reqDto.UserRequest;
import com.swp.entities.Post;
import com.swp.entities.Role;
import com.swp.entities.User;
import com.swp.exception.EntityNotFoundException;
import com.swp.repositories.RoleRepository;
import com.swp.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private Integer userId;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @PostConstruct
    public void initialize() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User userDetails = (User) authentication.getPrincipal();
            userId = userDetails.getUsId();
        }
    }

    public User getById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        Integer userId = userDetails.getUsId();
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", userId.toString()));
    }

    public User getById(Integer id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", id.toString()) );
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public List<User> filterUsersByDateRange(List<User> users, LocalDateTime startDate, LocalDateTime endDate) {
        return users.stream()
                .filter(a -> a.getCreated_date().isAfter(startDate) && a.getCreated_date().isBefore(endDate))
                .collect(Collectors.toList());
    }

    public List<User> sortUsers(List<User> users, String sortBy, String sortDirection) {
        List<User> sortedUsers = new ArrayList<>(users);

        switch (sortBy) {
            case "likeCount":
                sortedUsers.sort(Comparator.comparingInt(user -> (int) user.getPosts().stream().flatMap(post -> post.getVotes().stream())
                        .filter(vote -> vote.getVoteType().getId() == 1).count()));
                break;
            case "dislikeCount":
                sortedUsers.sort(Comparator.comparingInt(user -> (int) user.getPosts().stream().flatMap(post -> post.getVotes().stream())
                        .filter(vote -> vote.getVoteType().getId() == 2).count()));
                break;
            case "createdDate":
                sortedUsers.sort(Comparator.comparing(User::getCreated_date));
                break;
            case "awardCount":
                sortedUsers.sort(Comparator.comparingInt(user -> (int) user.getPosts().stream().flatMap(post -> post.getAwards().stream()).count()));
                break;
            case "userContribution":
                sortedUsers.sort(Comparator.comparingInt(user -> (int) user.getContributionPoint()));
                break;
            case "numberOfApprovedPost":
                sortedUsers.sort(Comparator.comparingInt(user -> (int) user.getPosts().stream()
                        .filter(post -> "APPROVED".equalsIgnoreCase(post.getPostApprovals().getStatus())).count()));
                break;
            default:
                // Handle unsupported sortBy criteria.
                return users;
        }

        if ("desc".equalsIgnoreCase(sortDirection)) {
            Collections.reverse(sortedUsers);
        }

        return sortedUsers;
    }

    public User createUser(UserRequest userRequest) {
        String inputedEmail = userRequest.getEmail();
        if (inputedEmail != null) {
            if(userRepository.findByEmail(inputedEmail) != null) {
                throw new IllegalArgumentException("Email is already in use");
            } else {
                User user = new User();
                user.setDisplay_name(userRequest.getDisplay_name());
                user.setAdditional_info(userRequest.getAdditional_info());
                user.setPassword(userRequest.getPassword());
                user.setEmail(userRequest.getEmail());
                user.setCreated_date(LocalDateTime.now());

                Role role = roleRepository.findByRoleInfo(userRequest.getRole_id())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Role: " + userRequest.getRole_id()));
                user.setRole_id(role);

                user.setContributionPoint(0);

                return userRepository.save(user);
            }
        } else {
            throw new IllegalArgumentException("Email cannot be null");
        }
    }
//    public Boolean existsByUsername(String username) {
//        return userRepository.existsByUsername(username);
//    }

}
