package com.k8s.devops.repository;

import com.k8s.devops.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}