package com.civio_project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public String welcome() {
        return "Welcome to Civio Project API. Visit /swagger-ui.html for API documentation.";
    }
}