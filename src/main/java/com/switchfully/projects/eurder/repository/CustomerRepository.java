package com.switchfully.projects.eurder.repository;

import com.switchfully.projects.eurder.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepository {

    private final List<User> customerList;

    public CustomerRepository() {
        this.customerList = new ArrayList<>();
    }
}
