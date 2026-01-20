package com.coffeeshop.backend.service;

import com.coffeeshop.backend.dto.AuthResponse;
import com.coffeeshop.backend.dto.LoginRequest;
import com.coffeeshop.backend.dto.RegisterRequest;
import com.coffeeshop.backend.model.Role;
import com.coffeeshop.backend.model.User;
import com.coffeeshop.backend.repository.UserRepository;
import com.coffeeshop.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequest request){
        if (userRepository.existsByUsername(request.username())){
            throw new RuntimeException("Username already exists");
        }

        if(userRepository.existsByEmail(request.email())){
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);

        userRepository.save(user);

        return "User registered successfully";
    }

    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}
