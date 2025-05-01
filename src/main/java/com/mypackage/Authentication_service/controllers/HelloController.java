package com.mypackage.Authentication_service.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HelloController {
    @GetMapping("/")
    public String greet() {
        return "Hello World ";
    }
    
    
}
