package com.switchfully.projects.eurder.domain.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.switchfully.projects.eurder.security.UserRole;

public class UserDto {

    private String userId;
    private Name name;
    private Address address;
    private EmailAddress emailAddress;
    private PhoneNumber phoneNumber;
    private UserRole userRole;

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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
