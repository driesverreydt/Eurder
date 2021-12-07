package com.switchfully.projects.eurder.domain.item;

import com.switchfully.projects.eurder.domain.exception.InvalidItemInformationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ItemTest {

    private static String myName;
    private static String myDescription;
    private static double myPrice;
    private static int myAmount;

    @BeforeAll
    static void setup() {
        myName = "potato";
        myDescription = "A potato is a great ingredient for cooking";
        myPrice = 0.5;
        myAmount = 26;
    }

    @Test
    void givenItemInfoWithNoName_whenCreatingItem_thenExpectInvalidItemInformationException() {
        Assertions.assertThatExceptionOfType(InvalidItemInformationException.class)
                .isThrownBy(() -> new Item(null, myDescription, myPrice, myAmount));
    }

    @Test
    void givenItemInfoWithNoDescription_whenCreatingItem_thenExpectInvalidItemInformationException() {
        Assertions.assertThatExceptionOfType(InvalidItemInformationException.class)
                .isThrownBy(() -> new Item(myName, null, myPrice, myAmount));
    }

    @Test
    void givenItemInfoWithZeroPrice_whenCreatingItem_thenExpectInvalidItemInformationException() {
        Assertions.assertThatExceptionOfType(InvalidItemInformationException.class)
                .isThrownBy(() -> new Item(myName, myDescription, 0, myAmount));
    }

    @Test
    void givenItemInfoWithNegativePrice_whenCreatingItem_thenExpectInvalidItemInformationException() {
        Assertions.assertThatExceptionOfType(InvalidItemInformationException.class)
                .isThrownBy(() -> new Item(myName, myDescription, -1, myAmount));
    }

    @Test
    void givenItemInfoWithZeroAmount_whenCreatingItem_thenExpectInvalidItemInformationException() {
        Assertions.assertThatExceptionOfType(InvalidItemInformationException.class)
                .isThrownBy(() -> new Item(myName, myDescription, myPrice, 0));
    }

    @Test
    void givenItemInfoWithNegativeAmount_whenCreatingItem_thenExpectInvalidItemInformationException() {
        Assertions.assertThatExceptionOfType(InvalidItemInformationException.class)
                .isThrownBy(() -> new Item(myName, myDescription, myPrice, -1));
    }
}