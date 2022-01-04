package com.switchfully.projects.eurder.domain.order;

import com.switchfully.projects.eurder.domain.exception.InvalidItemGroupInformationException;
import com.switchfully.projects.eurder.domain.item.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ItemGroupTest {

    private static Item item;

    @BeforeAll
    static void setup() {
        item = new Item("name", "description", 1.50, 5);
    }

    @Test
    void givenItemGroupWithNoItemId_whenCreatingAnItemGroup_thenThrowInvalidItemGroupInformationException() {
        Assertions.assertThatExceptionOfType(InvalidItemGroupInformationException.class)
                .isThrownBy(() -> new ItemGroup(null, 5));
    }

    @Test
    void givenItemGroupWithZeroAmount_whenCreatingAnItemGroup_thenThrowInvalidItemGroupInformationException() {
        Assertions.assertThatExceptionOfType(InvalidItemGroupInformationException.class)
                .isThrownBy(() -> new ItemGroup(item, 0));
    }

    @Test
    void givenItemGroupWithNegativeAmount_whenCreatingAnItemGroup_thenThrowInvalidItemGroupInformationException() {
        Assertions.assertThatExceptionOfType(InvalidItemGroupInformationException.class)
                .isThrownBy(() -> new ItemGroup(item, -1));
    }

}