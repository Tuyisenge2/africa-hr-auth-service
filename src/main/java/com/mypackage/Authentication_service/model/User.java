package com.mypackage.Authentication_service.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    private String picture;

    @Column(name = "google_id", unique = true)
    private String googleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    @JsonIgnoreProperties("users")
    private Role role;

    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;

    @Column(name = "last_login")
    private java.time.LocalDateTime lastLogin;

    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
        lastLogin = java.time.LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastLogin = java.time.LocalDateTime.now();
    }
} 