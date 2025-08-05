package com.k8s.devops.controller;

import com.k8s.devops.model.User;
import com.k8s.devops.repository.UserRepository;
import com.k8s.devops.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HealthService healthService;

    @GetMapping("health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }

    @GetMapping("db-status")
    public Map<String, String> dbStatus() {
        boolean dbUp = healthService.isDatabaseUp();
        return Map.of("database", dbUp ? "UP" : "DOWN");
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}