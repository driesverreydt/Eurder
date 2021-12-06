package com.switchfully.projects.eurder.repository;

import com.switchfully.projects.eurder.domain.user.*;
import com.switchfully.projects.eurder.security.UserRole;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public class UserRepository {

    private final Collection<User> userList;

    public UserRepository() {
        this.userList = new ArrayList<>();
        User adminUser = new User(new Name("adminFirstName", "adminLastName"),
                new Address("adminStreet", 0, 0, "adminCity"),
                new EmailAddress("admin", "admin", "com"),
                new PhoneNumber("0000000000","Belgium"), UserRole.ADMIN);
        userList.add(adminUser);
    }

    public Collection<User> getAllUsers() {
        return userList;
    }

    public User addUser(User user) {
        userList.add(user);
        return user;
    }

    public User removeUser(User user) {
        userList.remove(user);
        return user;
    }

}
