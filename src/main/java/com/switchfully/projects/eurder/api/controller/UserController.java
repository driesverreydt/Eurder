package com.switchfully.projects.eurder.api.controller;

import com.switchfully.projects.eurder.api.dto.UserDto;
import com.switchfully.projects.eurder.security.AuthorizationService;
import com.switchfully.projects.eurder.security.EurderFeature;
import com.switchfully.projects.eurder.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthorizationService authorizationService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, AuthorizationService authorizationService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerCustomer(@RequestBody UserDto userDto, @RequestHeader(required = false) String authorization) {
        logger.info("Register customer method called");
        authorizationService.authorize(EurderFeature.CUSTOMER_CREATE, authorization);
        UserDto savedUserDto = userService.saveUserDto(userDto);
        logger.info("Register customer method successfully finished");
        return savedUserDto;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserDto> getAllCustomers(@RequestHeader(required = false) String authorization) {
        logger.info("View all customers method called");
        authorizationService.authorize(EurderFeature.CUSTOMER_VIEW_ALL, authorization);
        Collection<UserDto> customerDtos = userService.getAllCustomerDtos();
        logger.info("View all customers method successfully finished");
        return customerDtos;
    }

    @GetMapping(path = "/{userId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getCustomerById(@PathVariable String userId, @RequestHeader(required = false) String authorization) {
        logger.info("View customer by id method called");
        authorizationService.authorize(EurderFeature.CUSTOMER_VIEW_SINGLE, authorization);
        //TODO catch UUID parse error
        UserDto customerDto = userService.getCustomerDtoById(UUID.fromString(userId));
        logger.info("View customer by id method successfully finished");
        return customerDto;
    }
}
