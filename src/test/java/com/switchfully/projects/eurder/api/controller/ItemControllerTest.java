package com.switchfully.projects.eurder.api.controller;

import com.switchfully.projects.eurder.api.dto.ItemDto;
import com.switchfully.projects.eurder.api.dto.UserDto;
import com.switchfully.projects.eurder.domain.user.Address;
import com.switchfully.projects.eurder.domain.user.EmailAddress;
import com.switchfully.projects.eurder.domain.user.Name;
import com.switchfully.projects.eurder.domain.user.PhoneNumber;
import com.switchfully.projects.eurder.security.UserRole;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Base64;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ItemControllerTest {

    @Value("${server.port}")
    private int port;

    @Test
    void givenAnItemDtoToCreate_whenAddingAnItemAsAdmin_thenTheNewlyCreatedItemIsSavedAndReturned() {
        String itemName = "potato";
        String itemDescription = "";
        double itemPrice = 0.5;
        int itemAmount = 26;

        ItemDto createItemDto = new ItemDto.ItemDtoBuilder()
                .setName(itemName)
                .setDescription(itemDescription)
                .setPrice(itemPrice)
                .setAmount(itemAmount)
                .build();

        String authorization = "Basic " + Base64.getEncoder().encodeToString("admin@mail.com:adminpassword".getBytes());

        ItemDto createdItemDto =
                RestAssured
                        .given()
                        .header("Authorization", authorization)
                        .body(createItemDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/items")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(ItemDto.class);

        assertThat(createdItemDto.getItemId()).isNotNull();
        assertThat(createdItemDto.getName()).isEqualTo(itemName);
        assertThat(createdItemDto.getDescription()).isEqualTo(itemDescription);
        assertThat(createdItemDto.getPrice()).isEqualTo(itemPrice);
        assertThat(createdItemDto.getAmount()).isEqualTo(itemAmount);
    }

    @Test
    void givenAnItemDtoToCreate_whenAddingAnItemNotAsAdmin_thenRespondWithUnauthorizedAndMessage() {
        String itemName = "potato";
        String itemDescription = "";
        double itemPrice = 0.5;
        int itemAmount = 26;

        ItemDto createItemDto = new ItemDto.ItemDtoBuilder()
                .setName(itemName)
                .setDescription(itemDescription)
                .setPrice(itemPrice)
                .setAmount(itemAmount)
                .build();

        String responseMessage =
                RestAssured
                        .given()
                        .body(createItemDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/items")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .extract().path("message");

        Assertions.assertThat(responseMessage).isEqualTo("User with role " + "guest" +
                " does not have rights to feature " + "item_create");

        //add customer
        Name myName = new Name("Lies", "Lievens");
        Address myAddress = new Address("Stappersstraat", 20, 2500, "Luik");
        EmailAddress myEmailAddress = new EmailAddress("customer", "mail", "com");
        String myPassword = "customerpassword";
        PhoneNumber myPhoneNumber = new PhoneNumber("3333333333", "Belgium");

        UserDto createUserCustomerDto = new UserDto.UserDtoBuilder()
                .setName(myName)
                .setAddress(myAddress)
                .setEmailAddress(myEmailAddress)
                .setPassword(myPassword)
                .setPhoneNumber(myPhoneNumber)
                .setUserRole(UserRole.CUSTOMER)
                .build();

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

        String authorization = "Basic " + Base64.getEncoder().encodeToString("customer@mail.com:customerpassword".getBytes());

        String responseMessageCustomer =
                RestAssured
                        .given()
                        .header("Authorization", authorization)
                        .body(createItemDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/items")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .extract().path("message");

        Assertions.assertThat(responseMessageCustomer).isEqualTo("User with role " + "customer" +
                " does not have rights to feature " + "item_create");
    }

    @Test
    void givenAWrongUsername_whenAddingAnItemAsAdmin_thenRespondWithUnauthorizedAndMessage() {
        String itemName = "potato";
        String itemDescription = "";
        double itemPrice = 0.5;
        int itemAmount = 26;

        ItemDto createItemDto = new ItemDto.ItemDtoBuilder()
                .setName(itemName)
                .setDescription(itemDescription)
                .setPrice(itemPrice)
                .setAmount(itemAmount)
                .build();

        String authorization = "Basic " + Base64.getEncoder().encodeToString("notauser@mail.com:adminpassword".getBytes());

        String responseMessage =
                RestAssured
                        .given()
                        .header("Authorization", authorization)
                        .body(createItemDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/items")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .extract().path("message");

        Assertions.assertThat(responseMessage).isEqualTo("No user found with email " + "notauser@mail.com");
    }

    @Test
    void givenAWrongPassword_whenAddingAnItemAsAdmin_thenRespondWithUnauthorizedAndMessage() {
        String itemName = "potato";
        String itemDescription = "";
        double itemPrice = 0.5;
        int itemAmount = 26;

        ItemDto createItemDto = new ItemDto.ItemDtoBuilder()
                .setName(itemName)
                .setDescription(itemDescription)
                .setPrice(itemPrice)
                .setAmount(itemAmount)
                .build();

        String authorization = "Basic " + Base64.getEncoder().encodeToString("admin@mail.com:notadminpassword".getBytes());

        String responseMessage =
                RestAssured
                        .given()
                        .header("Authorization", authorization)
                        .body(createItemDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/items")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .extract().path("message");

        Assertions.assertThat(responseMessage).isEqualTo("Password " + "notadminpassword" +
                " does not match the password for user with email " + "admin@mail.com");
    }

    @Test
    void givenAnItemWithNoName_whenAddingAnItemAsAdmin_thenRespondWithBadRequestAndMessage() {
        String itemDescription = "";
        double itemPrice = 0.5;
        int itemAmount = 26;

        ItemDto createItemDto = new ItemDto.ItemDtoBuilder()
                .setDescription(itemDescription)
                .setPrice(itemPrice)
                .setAmount(itemAmount)
                .build();

        String authorization = "Basic " + Base64.getEncoder().encodeToString("admin@mail.com:adminpassword".getBytes());

        String responseMessage =
                RestAssured
                        .given()
                        .header("Authorization", authorization)
                        .body(createItemDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/items")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .extract().path("message");

        Assertions.assertThat(responseMessage).isEqualTo("An item requires a name");
    }
}