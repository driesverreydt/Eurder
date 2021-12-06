package com.switchfully.projects.eurder.api;

import com.switchfully.projects.eurder.security.AuthenticationService;
import com.switchfully.projects.eurder.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private final CustomerService customerService;
    private final AuthenticationService authenticationService;

    @Autowired
    public CustomerController(CustomerService customerService, AuthenticationService authenticationService) {
        this.customerService = customerService;
        this.authenticationService = authenticationService;
    }
}
