package com.switchfully.projects.eurder.repository;

import com.switchfully.projects.eurder.domain.user.*;
import com.switchfully.projects.eurder.security.UserRole;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public class UserRepository {

    private final Collection<User> userCollection;

    public UserRepository() {
        this.userCollection = new ArrayList<>();
        User adminUser = new User(new Name("adminFirstName", "adminLastName"),
                new Address("adminStreet", 0, 0, "adminCity"),
                new EmailAddress("admin", "mail", "com"),
                "adminpassword",
                new PhoneNumber("0000000000", "Belgium"), UserRole.ADMIN);
        userCollection.add(adminUser);
    }

    public Collection<User> getAllUsers() {
        return userCollection;
    }

    public User addUser(User user) {
        userCollection.add(user);
        return user;
    }
}
