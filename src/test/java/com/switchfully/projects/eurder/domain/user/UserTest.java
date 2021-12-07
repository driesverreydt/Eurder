package com.switchfully.projects.eurder.domain.user;

import com.switchfully.projects.eurder.domain.exception.InvalidUserInformationException;
import com.switchfully.projects.eurder.security.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserTest {

    private static Name myName;
    private static Address myAddress;
    private static EmailAddress myEmailAddress;
    private static PhoneNumber myPhoneNumber;
    private static UserRole myUserRole;

    @BeforeAll
    static void setup(){
        myName = new Name("Dries","Verreydt");
        myAddress = new Address("Elkenlaan", 55, 3000, "Leuven");
        myEmailAddress = new EmailAddress("driesv","hotmail","com");
        myPhoneNumber = new PhoneNumber("0123456789","Belgium");
        myUserRole = UserRole.ADMIN;
    }

    @Test
    void givenUserinfoWithNoName_whenCreatingUser_thenExpectInvalidUserInformationException(){
        Assertions.assertThatExceptionOfType(InvalidUserInformationException.class)
                .isThrownBy(() -> new User(null,myAddress,myEmailAddress,myPhoneNumber,myUserRole));
    }

    @Test
    void givenUserinfoWithNoFirstName_whenCreatingUser_thenExpectInvalidUserInformationException(){
        Name myNameWithNoFirstName = new Name(null,"Verreydt");
        Assertions.assertThatExceptionOfType(InvalidUserInformationException.class)
                .isThrownBy(() -> new User(myNameWithNoFirstName,myAddress,myEmailAddress,myPhoneNumber,myUserRole));
    }

    @Test
    void givenUserinfoWithNoLastName_whenCreatingUser_thenExpectInvalidUserInformationException(){
        Name myNameWithNoLastName = new Name("Dries",null);
        Assertions.assertThatExceptionOfType(InvalidUserInformationException.class)
                .isThrownBy(() -> new User(myNameWithNoLastName,myAddress,myEmailAddress,myPhoneNumber,myUserRole));
    }

    @Test
    void givenUserinfoWithNoAddress_whenCreatingUser_thenExpectInvalidUserInformationException(){
        Assertions.assertThatExceptionOfType(InvalidUserInformationException.class)
                .isThrownBy(() -> new User(myName,null,myEmailAddress,myPhoneNumber,myUserRole));
    }

    @Test
    void givenUserinfoWithNoEmailAddress_whenCreatingUser_thenExpectInvalidUserInformationException(){
        Assertions.assertThatExceptionOfType(InvalidUserInformationException.class)
                .isThrownBy(() -> new User(myName,myAddress,null,myPhoneNumber,myUserRole));
    }

    @Test
    void givenUserinfoWithNoPhoneNumber_whenCreatingUser_thenExpectInvalidUserInformationException(){
        Assertions.assertThatExceptionOfType(InvalidUserInformationException.class)
                .isThrownBy(() -> new User(myName,myAddress,myEmailAddress,null,myUserRole));
    }

    @Test
    void givenUserinfoWithNoUserRole_whenCreatingUser_thenExpectInvalidUserInformationException(){
        Assertions.assertThatExceptionOfType(InvalidUserInformationException.class)
                .isThrownBy(() -> new User(myName,myAddress,myEmailAddress,myPhoneNumber,null));
    }
}