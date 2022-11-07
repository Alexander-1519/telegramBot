package com.rybaq.telegrambot.service;

import com.rybaq.telegrambot.entity.User;
import com.rybaq.telegrambot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        if(!userRepository.existsByUserId(user.getUserId())) {
            userRepository.save(user);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
