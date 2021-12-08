package com.switchfully.projects.eurder.api.controller;

import com.switchfully.projects.eurder.api.dto.ItemDto;
import com.switchfully.projects.eurder.api.dto.UserDto;
import com.switchfully.projects.eurder.domain.user.*;
import com.switchfully.projects.eurder.security.UserRole;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Base64;
import java.util.Collection;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {

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
    void givenAnIncompleteUserCustomerDtoToCreate_whenCreatingUserCustomer_thenRespondWithBadRequestAndMessage() {
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

    @Test
    void givenASystemWithCustomers_whenRequestingAnOverviewOfAllCustomersAsAdmin_thenRespondWithListOfAllCustomers(){
        //Add customer 1
        Name myName1 = new Name("Dries", "Verreydt");
        Address myAddress1 = new Address("Vaartstraat", 61, 3000, "Leuven");
        EmailAddress myEmailAddress1 = new EmailAddress("driesvv", "hotmail", "com");
        String myPassword1 = "password";
        PhoneNumber myPhoneNumber1 = new PhoneNumber("0123456789", "Belgium");

        UserDto createUserCustomerDto1 = new UserDto.UserDtoBuilder()
                .setName(myName1)
                .setAddress(myAddress1)
                .setEmailAddress(myEmailAddress1)
                .setPassword(myPassword1)
                .setPhoneNumber(myPhoneNumber1)
                .setUserRole(UserRole.CUSTOMER)
                .build();

        UserDto createdUserCustomerDto1 =
                RestAssured
                        .given()
                        .body(createUserCustomerDto1)
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

        //Add customer 2
        Name myName2 = new Name("Jan", "Berghmans");
        Address myAddress2 = new Address("Kippenlaan", 55, 2000, "Antwerpen");
        EmailAddress myEmailAddress2 = new EmailAddress("janB", "gmail", "com");
        String myPassword2 = "password2";
        PhoneNumber myPhoneNumber2 = new PhoneNumber("2222222222", "Belgium");

        UserDto createUserCustomerDto2 = new UserDto.UserDtoBuilder()
                .setName(myName2)
                .setAddress(myAddress2)
                .setEmailAddress(myEmailAddress2)
                .setPassword(myPassword2)
                .setPhoneNumber(myPhoneNumber2)
                .setUserRole(UserRole.CUSTOMER)
                .build();

        UserDto createdUserCustomerDto2 =
                RestAssured
                        .given()
                        .body(createUserCustomerDto2)
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

        //Add customer
        Name myName3 = new Name("Lies", "Lievens");
        Address myAddress3 = new Address("Stappersstraat", 20, 2500, "Luik");
        EmailAddress myEmailAddress3 = new EmailAddress("lielie", "hotmail", "com");
        String myPassword3 = "password3";
        PhoneNumber myPhoneNumber3 = new PhoneNumber("3333333333", "Belgium");

        UserDto createUserCustomerDto3 = new UserDto.UserDtoBuilder()
                .setName(myName3)
                .setAddress(myAddress3)
                .setEmailAddress(myEmailAddress3)
                .setPassword(myPassword3)
                .setPhoneNumber(myPhoneNumber3)
                .setUserRole(UserRole.CUSTOMER)
                .build();

        UserDto createdUserCustomerDto3 =
                RestAssured
                        .given()
                        .body(createUserCustomerDto3)
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

        //getCustomers
        String authorization = "Basic " + Base64.getEncoder().encodeToString("admin@mail.com:adminpassword".getBytes());

        Collection<UserDto> customerCollection =
                RestAssured
                        .given()
                        .header("Authorization", authorization)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .get("/users")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .body()
                        .jsonPath()
                        .getList(".", UserDto.class);

        assertThat(collectionContains(customerCollection,createdUserCustomerDto1)).isTrue();
        assertThat(collectionContains(customerCollection,createdUserCustomerDto2)).isTrue();
        assertThat(collectionContains(customerCollection,createdUserCustomerDto3)).isTrue();
        assertThat(customerCollection.size()).isEqualTo(3);
    }

    @Test
    void givenASystemWithCustomer_whenRequestingADetailViewOfCustomerAsAdmin_thenRespondWithDetailsOfCustomer() {
        //Add customer
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

        //check customer by id
        String authorization = "Basic " + Base64.getEncoder().encodeToString("admin@mail.com:adminpassword".getBytes());

        UserDto customer =
                RestAssured
                        .given()
                        .header("Authorization", authorization)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .get("/users/"+createdUserCustomerDto.getUserId())
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(UserDto.class);

        assertThat(customersAreTheSame(createdUserCustomerDto,customer)).isTrue();
    }

    @Test
    void givenCustomerNotInSystem_whenRequestingToViewCustomer_thenBadRequestResponseIsReturnedWithMessage(){
        String authorization = "Basic " + Base64.getEncoder().encodeToString("admin@mail.com:adminpassword".getBytes());

        String responseMessage =
                RestAssured
                        .given()
                        .header("Authorization", authorization)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .get("/users/"+"notauserid")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .extract().path("message");

        Assertions.assertThat(responseMessage).isEqualTo("Customer with id " + "notauserid" + " could not be found");
    }

    private boolean collectionContains(Collection<UserDto> customerCollection, UserDto customer){
        for(UserDto customerInCollection : customerCollection){
            if(customersAreTheSame(customerInCollection,customer)){
                return true;
            }
        }
        return false;
    }

    private boolean customersAreTheSame(UserDto customer1, UserDto customer2){
        if(!customer1.getUserId().equals(customer2.getUserId())){
            return false;
        }
        if(!customer1.getName().equals(customer2.getName())){
            return false;
        }
        if(!customer1.getAddress().equals(customer2.getAddress())){
            return false;
        }
        if(!customer1.getEmailAddress().equals(customer2.getEmailAddress())){
            return false;
        }
        if(!customer1.getPassword().equals(customer2.getPassword())){
            return false;
        }
        if(!customer1.getPhoneNumber().equals(customer2.getPhoneNumber())){
            return false;
        }
        if(!customer1.getUserRole().equals(customer2.getUserRole())){
            return false;
        }
        return true;
    }
}