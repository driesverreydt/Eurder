package com.switchfully.projects.eurder.security;

import com.switchfully.projects.eurder.domain.exception.AuthorisationNotGrantedException;
import com.switchfully.projects.eurder.domain.user.*;
import com.switchfully.projects.eurder.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

class AuthorizationServiceTest {

    private AuthorizationService authorizationService;

    @BeforeEach
    void setup() {
        UserService mockUserService = Mockito.mock(UserService.class);
        List<User> userList = new ArrayList<>();
        User user = new User(new Name("first", "last"),
                new Address("street", 5, 2000, "city"),
                new EmailAddress("admin", "mail", "com"),
                "adminpassword",
                new PhoneNumber("123456789", "Belgium"),
                UserRole.ADMIN);
        userList.add(user);
        Mockito.when(mockUserService.getUsersByEmail(new EmailAddress("admin", "mail", "com")))
                .thenReturn(userList);
        authorizationService = new AuthorizationService(mockUserService);
    }

    @Test
    void givenCorrectUserInfoForAdmin_whenAuthorizing_thenGiveAdminAuthorizationLevel2() {
        String authorization = "Basic " + Base64.getEncoder().encodeToString("admin@mail.com:adminpassword".getBytes());
        Assertions.assertThat(authorizationService.getAuthorizationLevel(authorization)).isEqualTo(UserRole.ADMIN.getAuthorizationLevel());
    }

    @Test
    void givenNotExistingUsersEmail_whenAuthorizing_thenExpectAuthorizationNotGrantedException() {
        String authorization = "Basic " + Base64.getEncoder().encodeToString("notauser@mail.com:password".getBytes());
        Assertions.assertThatExceptionOfType(AuthorisationNotGrantedException.class)
                .isThrownBy(() -> authorizationService.getAuthorizationLevel(authorization));
    }

    @Test
    void givenWrongPasswordForExistingUser_whenAuthorizing_thenExpectAuthorizationNotGrantedException() {
        String authorization = "Basic " + Base64.getEncoder().encodeToString("admin@mail.com:wrongpassword".getBytes());
        Assertions.assertThatExceptionOfType(AuthorisationNotGrantedException.class)
                .isThrownBy(() -> authorizationService.getAuthorizationLevel(authorization));
    }
}