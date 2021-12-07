package com.switchfully.projects.eurder.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.switchfully.projects.eurder.domain.user.Address;
import com.switchfully.projects.eurder.domain.user.EmailAddress;
import com.switchfully.projects.eurder.domain.user.Name;
import com.switchfully.projects.eurder.domain.user.PhoneNumber;
import com.switchfully.projects.eurder.security.UserRole;

@JsonDeserialize(builder = UserDto.UserDtoBuilder.class)
public class UserDto {

    private final String userId;
    private final Name name;
    private final Address address;
    private final EmailAddress emailAddress;
    private final String password;
    private final PhoneNumber phoneNumber;
    private final UserRole userRole;

    private UserDto(UserDtoBuilder userDtoBuilder) {
        this.userId = userDtoBuilder.userId;
        this.name = userDtoBuilder.name;
        this.address = userDtoBuilder.address;
        this.emailAddress = userDtoBuilder.emailAddress;
        this.password = userDtoBuilder.password;
        this.phoneNumber = userDtoBuilder.phoneNumber;
        this.userRole = userDtoBuilder.userRole;
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

    @JsonPOJOBuilder(withPrefix = "set")
    public static class UserDtoBuilder {

        private String userId;
        private Name name;
        private Address address;
        private EmailAddress emailAddress;
        private String password;
        private PhoneNumber phoneNumber;
        private UserRole userRole;

        public UserDtoBuilder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserDtoBuilder setName(Name name) {
            this.name = name;
            return this;
        }

        public UserDtoBuilder setAddress(Address address) {
            this.address = address;
            return this;
        }

        public UserDtoBuilder setEmailAddress(EmailAddress emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public UserDtoBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserDtoBuilder setPhoneNumber(PhoneNumber phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserDtoBuilder setUserRole(UserRole userRole) {
            this.userRole = userRole;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }
}
