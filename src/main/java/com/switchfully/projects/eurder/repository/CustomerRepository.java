package com.switchfully.projects.eurder.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepository {

    private final List<Customer> customerList;

    public CustomerRepository() {
        this.customerList = new ArrayList<>();
    }
}
