package com.swp.services;

import com.swp.cms.resDto.AuthenticationResponse;
import com.swp.cms.reqDto.AuthenticationRequest;
import com.swp.cms.reqDto.RegisterRequest;
import com.swp.config.JwtService;
import com.swp.entities.Role;
import com.swp.entities.User;
import com.swp.repositories.RoleRepository;
import com.swp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    public AuthenticationResponse register(RegisterRequest request) {
        // Find the Role object based on the role name from the request
        Role role = roleRepository.findByRoleInfo(request.getRole_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Role"));

        // Create the User object with the associated Role
        var user = User.builder()
                .display_name(request.getDisplayName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .additional_info(request.getAdditional_info())
                .role_id(role)
                .created_date(LocalDateTime.now())
                .build();

        // Save the User object to persist it in the database
        userRepository.save(user);

        // Generate the JWT token
        var jwtToken = jwtService.generateToken(user);

        // Build and return the AuthenticationResponse
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
