package com.k8s.devops.service;

import com.k8s.devops.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    @Autowired
    private UserRepository userRepository;

    public boolean isDatabaseUp() {
        try {
            userRepository.count();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}