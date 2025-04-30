package com.mypackage.Authentication_service.service;

import com.mypackage.Authentication_service.model.Role;
import com.mypackage.Authentication_service.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        // Create default roles if they don't exist
        createRoleIfNotExists("STAFF");
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("MANAGER");
    }

    private void createRoleIfNotExists(String roleName) {
        if (!roleRepository.findByName(roleName).isPresent()) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
    }

    public Role getDefaultRole() {
        return roleRepository.findByName("STAFF")
                .orElseThrow(() -> new RuntimeException("Default role 'STAFF' not found"));
    }
} 