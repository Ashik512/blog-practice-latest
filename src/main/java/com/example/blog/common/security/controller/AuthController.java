package com.example.blog.common.security.controller;

import com.example.blog.common.security.dto.AuthRequest;
import com.example.blog.common.security.dto.AuthResponse;
import com.example.blog.common.security.helper.JwtUtil;
import com.example.blog.common.security.service.UserDetailsImpl;
import com.example.blog.features.role.model.Role;
import com.example.blog.features.role.repository.RoleRepository;
import com.example.blog.features.user.model.User;
import com.example.blog.features.user.payload.request.UserRequestDto;
import com.example.blog.features.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        String jwt = jwtUtil.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new AuthResponse(userDetails.getUsername(), jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDto userRequest) {
        if (userRepository.existsByName(userRequest.getName())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        Role role = roleRepository.findById(userRequest.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found!"));

        // Create new user with hashed password
        User user = new User();
        user.setName(userRequest.getName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(role);

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}
