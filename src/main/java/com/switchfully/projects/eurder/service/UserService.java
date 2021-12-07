package com.switchfully.projects.eurder.service;

import com.switchfully.projects.eurder.domain.user.EmailAddress;
import com.switchfully.projects.eurder.domain.user.User;
import com.switchfully.projects.eurder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<User> getUsersByUserId(String userId) {
        return userRepository.getAllUsers().stream()
                .filter(user -> user.getUserId().equals(userId))
                .toList();
    }

    public Collection<User> getUsersByEmail(EmailAddress userEmailAddress) {
        return userRepository.getAllUsers().stream()
                .filter(user -> user.getEmailAddress().equals(userEmailAddress))
                .toList();
    }

    public User saveUser(User user) {
        return userRepository.addUser(user);
    }
}
