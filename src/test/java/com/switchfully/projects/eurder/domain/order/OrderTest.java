package com.switchfully.projects.eurder.domain.order;

import com.switchfully.projects.eurder.domain.exception.InvalidOrderInformationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

class OrderTest {

    @Test
    void givenOrderWithNoUserId_whenCreatingAnOrder_thenThrowInvalidOrderInformationException(){
        Collection<ItemGroup> myItemGroups = new ArrayList<>();
        myItemGroups.add(new ItemGroup("itemId1",5));

        Assertions.assertThatExceptionOfType(InvalidOrderInformationException.class)
                .isThrownBy(() -> new Order(null, myItemGroups));
    }

    @Test
    void givenOrderWithNoItemGroups_whenCreatingAnOrder_thenThrowInvalidOrderInformationException(){
        Assertions.assertThatExceptionOfType(InvalidOrderInformationException.class)
                .isThrownBy(() -> new Order("userId",null));
    }

    @Test
    void givenOrderWithEmptyItemGroups_whenCreatingAnOrder_thenThrowInvalidOrderInformationException(){
        Collection<ItemGroup> myItemGroups = new ArrayList<>();

        Assertions.assertThatExceptionOfType(InvalidOrderInformationException.class)
                .isThrownBy(() -> new Order("userId", myItemGroups));
    }
}