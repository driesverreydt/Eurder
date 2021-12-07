package com.switchfully.projects.eurder.domain.user;

import com.switchfully.projects.eurder.domain.exception.InvalidUserInformationException;
import com.switchfully.projects.eurder.security.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class User {

    private final String userId;
    private final Name name;
    private final Address address;
    private final EmailAddress emailAddress;
    private final String password;
    private final PhoneNumber phoneNumber;
    private final UserRole userRole;
    private final Logger logger = LoggerFactory.getLogger(User.class);

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
            logger.info("At user creation name was null");
            throw new InvalidUserInformationException("A user requires a name");
        }
        if (name.getFirstName() == null) {
            logger.info("At user creation first name was null");
            throw new InvalidUserInformationException("A user requires a first name");
        }
        if (name.getLastName() == null) {
            logger.info("At user creation last name was null");
            throw new InvalidUserInformationException("A user requires a last name");
        }
        if (address == null) {
            logger.info("At user creation address was null");
            throw new InvalidUserInformationException("A user requires an address");
        }
        if (emailAddress == null) {
            logger.info("At user creation email address was null");
            throw new InvalidUserInformationException("A user requires an email address");
        }
        if (password == null) {
            logger.info("At user creation password was null");
            throw new InvalidUserInformationException("A user requires a password");
        }
        if (phoneNumber == null) {
            logger.info("At user creation phone number was null");
            throw new InvalidUserInformationException("A user requires a phone number");
        }
        if (userRole == null) {
            logger.info("At user creation user role was null");
            throw new InvalidUserInformationException("A user requires a user role");
        }
    }
}
