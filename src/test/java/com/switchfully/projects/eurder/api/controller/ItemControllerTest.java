package com.switchfully.projects.eurder.api.controller;

import com.switchfully.projects.eurder.api.dto.ItemDto;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Base64;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
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

        assertThat(createdItemDto.getItemId()).isNotBlank();
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
}