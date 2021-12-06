package com.switchfully.projects.eurder.domain.user;

import com.switchfully.projects.eurder.security.UserRole;

import java.util.UUID;

public class User {

    private final String userId;
    private final Name name;
    private final Address address;
    private final EmailAddress emailAddress;
    private final PhoneNumber phoneNumber;
    private final UserRole userRole;

    public User(Name name, Address address, EmailAddress emailAddress, PhoneNumber phoneNumber, UserRole userRole) {
        this.userId = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.userRole = userRole;
    }

    public String getUserId() {
        return userId;
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public UserRole getUserRole() {
        return userRole;
    }
}
