package com.example.fulldev.service;

import com.example.fulldev.model.User;
import com.example.fulldev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findFirstById(id);
    }
}
