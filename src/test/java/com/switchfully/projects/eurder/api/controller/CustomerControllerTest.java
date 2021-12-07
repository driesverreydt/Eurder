package com.switchfully.projects.eurder.api.controller;

import com.switchfully.projects.eurder.api.dto.UserDto;
import com.switchfully.projects.eurder.domain.user.*;
import com.switchfully.projects.eurder.security.UserRole;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CustomerControllerTest {

    @Value("${server.port}")
    private int port;

    @Test
    void givenAUserCustomerDtoToCreate_whenAddingAUserCustomer_thenTheNewlyCreatedUserCustomerIsSavedAndReturned() {
        Name myName = new Name("Dries", "Verreydt");
        Address myAddress = new Address("Vaartstraat", 61, 3000, "Leuven");
        EmailAddress myEmailAddress = new EmailAddress("driesvv", "hotmail", "com");
        String myPassword = "password";
        PhoneNumber myPhoneNumber = new PhoneNumber("0123456789", "Belgium");

        UserDto createUserCustomerDto = new UserDto.UserDtoBuilder()
                .setName(myName)
                .setAddress(myAddress)
                .setEmailAddress(myEmailAddress)
                .setPassword(myPassword)
                .setPhoneNumber(myPhoneNumber)
                .setUserRole(UserRole.CUSTOMER)
                .build();

        UserDto createdUserCustomerDto =
                RestAssured
                        .given()
                        .body(createUserCustomerDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/users")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(UserDto.class);

        assertThat(createdUserCustomerDto.getUserId()).isNotBlank();
        assertThat(createdUserCustomerDto.getName()).isEqualTo(myName);
        assertThat(createdUserCustomerDto.getAddress()).isEqualTo(myAddress);
        assertThat(createdUserCustomerDto.getEmailAddress()).isEqualTo(myEmailAddress);
        assertThat(createdUserCustomerDto.getPassword()).isEqualTo(myPassword);
        assertThat(createdUserCustomerDto.getPhoneNumber()).isEqualTo(myPhoneNumber);
        assertThat(createdUserCustomerDto.getUserRole()).isEqualTo(UserRole.CUSTOMER);

    }

    @Test
    void createUserCustomer_givenAnIncompleteUserCustomerDtoToCreate_thenRespondWithBadRequestAndMessage() {
        Address myAddress = new Address("Vaartstraat", 61, 3000, "Leuven");
        EmailAddress myEmailAddress = new EmailAddress("driesvv", "hotmail", "com");
        String myPassword = "password";
        PhoneNumber myPhoneNumber = new PhoneNumber("0123456789", "Belgium");

        UserDto createUserCustomerDto = new UserDto.UserDtoBuilder()
                .setAddress(myAddress)
                .setEmailAddress(myEmailAddress)
                .setPassword(myPassword)
                .setPhoneNumber(myPhoneNumber)
                .setUserRole(UserRole.CUSTOMER)
                .build();

        String responseMessage =
                RestAssured
                        .given()
                        .body(createUserCustomerDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/users")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .extract().path("message");

        Assertions.assertThat(responseMessage).isEqualTo("A user requires a name");


    }
}