package com.switchfully.projects.eurder.service;

import com.switchfully.projects.eurder.api.dto.UserDto;
import com.switchfully.projects.eurder.api.mapper.UserMapper;
import com.switchfully.projects.eurder.domain.exception.NoSuchCustomerException;
import com.switchfully.projects.eurder.domain.user.EmailAddress;
import com.switchfully.projects.eurder.domain.user.User;
import com.switchfully.projects.eurder.repository.UserRepositoryInterface;
import com.switchfully.projects.eurder.security.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepositoryInterface userRepository;
    private final UserMapper userMapper;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepositoryInterface userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto saveUserDto(UserDto userDto) {
        User userToSave = userMapper.mapUserDtoToUser(userDto);
        User savedUser = userRepository.save(userToSave);
        UserDto savedUserDto = userMapper.mapUserToUserDto(savedUser);
        return savedUserDto;
    }

    public Collection<UserDto> getAllCustomerDtos() {
        return userRepository.findAll().stream()
                .filter(user -> user.getUserRole().equals(UserRole.CUSTOMER))
                .map(userMapper::mapUserToUserDto)
                .toList();
    }

    public UserDto getCustomerDtoById(UUID userId) {
        UserDto customer;
        try {
            customer = getCustomerDtosById(userId).iterator().next();
        } catch (NoSuchElementException ex) {
            logger.error("Customer with id " + userId + "could not be found");
            throw new NoSuchCustomerException("Customer with id " + userId + " could not be found");
        }
        return customer;
    }

    public Collection<UserDto> getCustomerDtosById(UUID userId) {
        return getAllCustomerDtos().stream()
                .filter(user -> user.getUserId().equals(userId))
                .toList();
    }

    public Collection<User> getUsersByUserId(UUID userId) {
        return userRepository.findAll().stream()
                .filter(user -> user.getUserId().equals(userId))
                .toList();
    }

    public Collection<User> getUsersByEmail(EmailAddress userEmailAddress) {
        return userRepository.findAll().stream()
                .filter(user -> user.getEmailAddress().equals(userEmailAddress))
                .toList();
    }

    public User getUserById(UUID userId) {
        User user;
        try {
            user = getUsersByUserId(userId).iterator().next();
        } catch (NoSuchElementException ex) {
            logger.error("User with id " + userId + "could not be found");
            throw new NoSuchCustomerException("User with id " + userId + " could not be found");
        }
        return user;
    }
}
