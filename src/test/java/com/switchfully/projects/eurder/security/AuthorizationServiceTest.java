package com.switchfully.projects.eurder.security;

import com.switchfully.projects.eurder.domain.exception.AuthorisationNotGrantedException;
import com.switchfully.projects.eurder.repository.UserRepository;
import com.switchfully.projects.eurder.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

class AuthorizationServiceTest {

    private AuthorizationService authorizationService;

    @BeforeEach
    void setup() {
        authorizationService = new AuthorizationService(new UserService(new UserRepository()));
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