package com.switchfully.projects.eurder.service;

import com.switchfully.projects.eurder.domain.user.EmailAddress;
import com.switchfully.projects.eurder.domain.user.User;
import com.switchfully.projects.eurder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(EmailAddress userEmailAddress) {
        return userRepository.getAllUsers().stream()
                .filter(user -> user.getEmailAddress().equals(userEmailAddress))
                .findFirst()
                .orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.addUser(user);
    }
}
