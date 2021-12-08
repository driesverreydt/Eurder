package com.switchfully.projects.eurder.api.controller;

import com.switchfully.projects.eurder.api.mapper.UserMapper;
import com.switchfully.projects.eurder.domain.user.User;
import com.switchfully.projects.eurder.api.dto.UserDto;
import com.switchfully.projects.eurder.security.AuthorizationService;
import com.switchfully.projects.eurder.security.EurderFeature;
import com.switchfully.projects.eurder.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthorizationService authorizationService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, UserMapper userMapper, AuthorizationService authorizationService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authorizationService = authorizationService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerCustomer(@RequestBody UserDto userDto, @RequestHeader(required = false) String authorization) {
        logger.info("Register customer method called");
        authorizationService.authorize(EurderFeature.CUSTOMER_CREATE, authorization);
        User user = userMapper.mapUserDtoToUser(userDto);
        User savedUser = userService.saveUser(user);
        UserDto savedUserDto = userMapper.mapUserToUserDto(savedUser);
        logger.info("Register customer method successfully finished");
        return savedUserDto;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserDto> getAllCustomers(@RequestHeader(required = false) String authorization) {
        logger.info("View all customers method called");
        authorizationService.authorize(EurderFeature.CUSTOMER_VIEW_ALL, authorization);
        Collection<User> customers = userService.getAllCustomers();
        Collection<UserDto> customerDtos = customers.stream()
                .map(userMapper::mapUserToUserDto)
                .toList();
        logger.info("View all customers method successfully finished");
        return customerDtos;
    }
}
