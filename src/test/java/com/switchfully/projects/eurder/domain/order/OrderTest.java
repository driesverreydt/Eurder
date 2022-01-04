package com.switchfully.projects.eurder.domain.order;

import com.switchfully.projects.eurder.domain.exception.InvalidOrderInformationException;
import com.switchfully.projects.eurder.domain.item.Item;
import com.switchfully.projects.eurder.domain.user.*;
import com.switchfully.projects.eurder.security.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

class OrderTest {

    private static User user;
    private static Item item;

    @BeforeAll
    static void setup() {
        user = new User(new Name("first", "last"),
                new Address("street", 5, 2000, "city"),
                new EmailAddress("user", "gmail", "com"),
                "password",
                new PhoneNumber("123456789", "Belgium"),
                UserRole.CUSTOMER);
        item = new Item("name", "description", 1.50, 5);
    }

    @Test
    void givenOrderWithNoUserId_whenCreatingAnOrder_thenThrowInvalidOrderInformationException() {
        Collection<ItemGroup> myItemGroups = new ArrayList<>();
        myItemGroups.add(new ItemGroup(item, 5));

        Assertions.assertThatExceptionOfType(InvalidOrderInformationException.class)
                .isThrownBy(() -> new Order(null, myItemGroups));
    }

    @Test
    void givenOrderWithNoItemGroups_whenCreatingAnOrder_thenThrowInvalidOrderInformationException() {
        Assertions.assertThatExceptionOfType(InvalidOrderInformationException.class)
                .isThrownBy(() -> new Order(user, null));
    }

    @Test
    void givenOrderWithEmptyItemGroups_whenCreatingAnOrder_thenThrowInvalidOrderInformationException() {
        Collection<ItemGroup> myItemGroups = new ArrayList<>();

        Assertions.assertThatExceptionOfType(InvalidOrderInformationException.class)
                .isThrownBy(() -> new Order(user, myItemGroups));
    }
}