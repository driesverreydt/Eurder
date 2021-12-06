package com.switchfully.projects.eurder.domain.user;

import com.switchfully.projects.eurder.security.UserRole;

public class SimpleDto {
    private String string1;
    private String string2;
    private int number1;
    private Name name;
    private Address address;
    private UserRole userRole;
    private EmailAddress emailAddress;
    private PhoneNumber phoneNumber;

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getString1() {
        return string1;
    }

    public String getString2() {
        return string2;
    }

    public int getNumber1() {
        return number1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }
}
