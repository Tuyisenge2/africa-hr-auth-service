package com.mypackage.Authentication_service.controllers;

import com.mypackage.Authentication_service.model.User;
import com.mypackage.Authentication_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllEmployees() {
        List<User> employees = userRepository.findAll();
        return ResponseEntity.ok(employees);
    }


    @GetMapping("/{email}")
    public ResponseEntity<User> getEmployeeByEmail(@PathVariable String email) {
        Optional<User> employee = userRepository.findByEmail(email);
        return employee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
} 