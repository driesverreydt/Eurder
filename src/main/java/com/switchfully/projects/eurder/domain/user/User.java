package com.switchfully.projects.eurder.domain.user;

import com.switchfully.projects.eurder.domain.exception.InvalidUserInformationException;
import com.switchfully.projects.eurder.security.UserRole;

import java.util.UUID;

public class User {

    private final String userId;
    private final Name name;
    private final Address address;
    private final EmailAddress emailAddress;
    private final String password;
    private final PhoneNumber phoneNumber;
    private final UserRole userRole;

    public User(Name name, Address address, EmailAddress emailAddress, String password, PhoneNumber phoneNumber, UserRole userRole) {
        userInformationValidation(name, address, emailAddress, password, phoneNumber, userRole);
        this.userId = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.emailAddress = emailAddress;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    private void userInformationValidation(Name name, Address address, EmailAddress emailAddress, String password,
                                           PhoneNumber phoneNumber, UserRole userRole) {
        if (name == null) {
            throw new InvalidUserInformationException("A user requires a name");
        }
        if (name.getFirstName() == null) {
            throw new InvalidUserInformationException("A user requires a first name");
        }
        if (name.getLastName() == null) {
            throw new InvalidUserInformationException("A user requires a last name");
        }
        if (address == null) {
            throw new InvalidUserInformationException("A user requires an address");
        }
        if (emailAddress == null) {
            throw new InvalidUserInformationException("A user requires an email address");
        }
        if (password == null) {
            throw new InvalidUserInformationException("A user requires a password");
        }
        if (phoneNumber == null) {
            throw new InvalidUserInformationException("A user requires a phone number");
        }
        if (userRole == null) {
            throw new InvalidUserInformationException("A user requires a user role");
        }
    }
}
