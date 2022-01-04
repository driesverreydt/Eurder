package com.switchfully.projects.eurder.api.controller;

import com.switchfully.projects.eurder.api.dto.ItemDto;
import com.switchfully.projects.eurder.api.dto.ItemGroupDto;
import com.switchfully.projects.eurder.api.dto.OrderDto;
import com.switchfully.projects.eurder.api.dto.UserDto;
import com.switchfully.projects.eurder.domain.user.Address;
import com.switchfully.projects.eurder.domain.user.EmailAddress;
import com.switchfully.projects.eurder.domain.user.Name;
import com.switchfully.projects.eurder.domain.user.PhoneNumber;
import com.switchfully.projects.eurder.security.UserRole;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderControllerTest {

    private UserDto customer;
    private ItemDto item1;
    private ItemDto item2;

    @Value("${server.port}")
    private int port;

    @BeforeEach
    void setup() {
        //Add customer
        Name myName = new Name("Dries", "Verreydt");
        Address myAddress = new Address("Vaartstraat", 61, 3000, "Leuven");
        EmailAddress myEmailAddress = new EmailAddress("driesvv", "hotmail", "com");
        String myPassword = "customerpassword";
        PhoneNumber myPhoneNumber = new PhoneNumber("0123456789", "Belgium");

        UserDto createUserCustomerDto = new UserDto.UserDtoBuilder()
                .setName(myName)
                .setAddress(myAddress)
                .setEmailAddress(myEmailAddress)
                .setPassword(myPassword)
                .setPhoneNumber(myPhoneNumber)
                .setUserRole(UserRole.CUSTOMER)
                .build();

        customer =
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

        //Add item 1
        String itemName1 = "potato";
        String itemDescription1 = "A potato";
        double itemPrice1 = 0.5;
        int itemAmount1 = 26;

        ItemDto createItemDto1 = new ItemDto.ItemDtoBuilder()
                .setName(itemName1)
                .setDescription(itemDescription1)
                .setPrice(itemPrice1)
                .setAmount(itemAmount1)
                .build();

        String authorization = "Basic " + Base64.getEncoder().encodeToString("admin@mail.com:adminpassword".getBytes());

        item1 =
                RestAssured
                        .given()
                        .header("Authorization", authorization)
                        .body(createItemDto1)
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

        //Add item 2
        String itemName2 = "tomato";
        String itemDescription2 = "A tomato";
        double itemPrice2 = 3.5;
        int itemAmount2 = 9;

        ItemDto createItemDto2 = new ItemDto.ItemDtoBuilder()
                .setName(itemName2)
                .setDescription(itemDescription2)
                .setPrice(itemPrice2)
                .setAmount(itemAmount2)
                .build();

        item2 =
                RestAssured
                        .given()
                        .header("Authorization", authorization)
                        .body(createItemDto2)
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
    }

    @Test
    void givenAnOrderDtoToCreate_whenAddingAnOrderAsCustomer_thenTheNewlyCreatedOrderIsSavedAndTheTotalOrderPriceIsReturned() {
        Collection<ItemGroupDto> itemGroupDtoCollection = new ArrayList<>();
        ItemGroupDto itemGroupDto1 = new ItemGroupDto.ItemGroupDtoBuilder()
                .setItemId(item1.getItemId())
                .setAmount(9)
                .build();
        ItemGroupDto itemGroupDto2 = new ItemGroupDto.ItemGroupDtoBuilder()
                .setItemId(item2.getItemId())
                .setAmount(10)
                .build();
        itemGroupDtoCollection.add(itemGroupDto1);
        itemGroupDtoCollection.add(itemGroupDto2);

        OrderDto createOrderDto = new OrderDto.OrderDtoBuilder()
                .setUserId(customer.getUserId())
                .setItemGroupDtoCollection(itemGroupDtoCollection)
                .build();

        String authorization = "Basic " + Base64.getEncoder().encodeToString("driesvv@hotmail.com:customerpassword".getBytes());

        String totalOrderCost =
                RestAssured
                        .given()
                        .header("Authorization", authorization)
                        .body(createOrderDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/orders")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .asString();

        assertThat(Double.parseDouble(totalOrderCost)).isEqualTo(39.5);
    }

    @Test
    void givenAnIncompleteOrder_whenAddingAnOrderAsCustomer_thenRespondWithBadRequestAndMessage() {
        OrderDto createOrderDto = new OrderDto.OrderDtoBuilder()
                .setUserId(customer.getUserId())
                .build();

        String authorization = "Basic " + Base64.getEncoder().encodeToString("driesvv@hotmail.com:customerpassword".getBytes());

        String responseMessage =
                RestAssured
                        .given()
                        .header("Authorization", authorization)
                        .body(createOrderDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/orders")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .extract().path("message");

        assertThat(responseMessage).isEqualTo("The item group collection in the order can not be null");
    }

    @Test
    void givenAnIncompleteItemGroup_whenAddingAnOrderAsCustomer_thenRespondWithBadRequestAndMessage() {
        Collection<ItemGroupDto> itemGroupDtoCollection = new ArrayList<>();
        ItemGroupDto itemGroupDto1 = new ItemGroupDto.ItemGroupDtoBuilder()
                .setItemId(item1.getItemId())
                .setAmount(9)
                .build();
        ItemGroupDto itemGroupDto2 = new ItemGroupDto.ItemGroupDtoBuilder()
                .setAmount(10)
                .build();
        itemGroupDtoCollection.add(itemGroupDto1);
        itemGroupDtoCollection.add(itemGroupDto2);

        OrderDto createOrderDto = new OrderDto.OrderDtoBuilder()
                .setUserId(customer.getUserId())
                .setItemGroupDtoCollection(itemGroupDtoCollection)
                .build();

        String authorization = "Basic " + Base64.getEncoder().encodeToString("driesvv@hotmail.com:customerpassword".getBytes());

        String responseMessage =
                RestAssured
                        .given()
                        .header("Authorization", authorization)
                        .body(createOrderDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/orders")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .extract().path("message");

        assertThat(responseMessage).isEqualTo("The itemId in an item group cannot be null");
    }
}