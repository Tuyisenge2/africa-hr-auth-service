package com.mypackage.Authentication_service.controllers;

import com.mypackage.Authentication_service.model.User;
import com.mypackage.Authentication_service.repository.UserRepository;
import com.mypackage.Authentication_service.service.JwtService;
import com.mypackage.Authentication_service.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class GoogleAuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Value("${frontend.url}")
    private String frontendUrl;

    @GetMapping("/google")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }

    @GetMapping("/google/callback")
    public void googleAuth(@AuthenticationPrincipal OAuth2User principal, HttpServletResponse response) throws IOException {
        String name = principal.getAttribute("name");
        String email = principal.getAttribute("email");
        String picture = principal.getAttribute("picture");
        String googleId = principal.getAttribute("sub");
        
        // Save or update user in database
        User user = userRepository.findByEmail(email)
                .orElse(new User());
        
        user.setEmail(email);
        user.setName(name);
        user.setPicture(picture);
        user.setGoogleId(googleId);
        
        // Set default role for new users
        if (user.getId() == null) {
            user.setRole(roleService.getDefaultRole());
        }
        
        userRepository.save(user);
        
        // Generate JWT token with role
        String token = jwtService.generateToken(email, name, picture, user.getRole().getName());
        
        // Redirect to dashboard with token as query parameter
        String redirectUrl = String.format("%s?token=%s", frontendUrl, token);
     response.sendRedirect(redirectUrl);
    }
} 

