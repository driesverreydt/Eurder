package com.switchfully.projects.eurder.domain.order;

import com.switchfully.projects.eurder.domain.exception.InvalidItemGroupInformationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ItemGroupTest {

    @Test
    void givenItemGroupWithNoItemId_whenCreatingAnItemGroup_thenThrowInvalidItemGroupInformationException(){
        Assertions.assertThatExceptionOfType(InvalidItemGroupInformationException.class)
                .isThrownBy(() -> new ItemGroup(null, 5));
    }

    @Test
    void givenItemGroupWithZeroAmount_whenCreatingAnItemGroup_thenThrowInvalidItemGroupInformationException(){
        Assertions.assertThatExceptionOfType(InvalidItemGroupInformationException.class)
                .isThrownBy(() -> new ItemGroup("itemId", 0));
    }

    @Test
    void givenItemGroupWithNegativeAmount_whenCreatingAnItemGroup_thenThrowInvalidItemGroupInformationException(){
        Assertions.assertThatExceptionOfType(InvalidItemGroupInformationException.class)
                .isThrownBy(() -> new ItemGroup("itemId", -1));
    }

    @Test
    void givenItemGroup_whenSettingZeroPriceSnapshot_thenThrowInvalidItemGroupInformationException(){
        ItemGroup itemGroup = new ItemGroup("userId",5);
        Assertions.assertThatExceptionOfType(InvalidItemGroupInformationException.class)
                .isThrownBy(() -> itemGroup.setPriceSnapshot(0.0));
    }

    @Test
    void givenItemGroup_whenSettingNegativePriceSnapshot_thenThrowInvalidItemGroupInformationException(){
        ItemGroup itemGroup = new ItemGroup("userId",5);
        Assertions.assertThatExceptionOfType(InvalidItemGroupInformationException.class)
                .isThrownBy(() -> itemGroup.setPriceSnapshot(-1.0));
    }

    @Test
    void givenItemGroup_whenSettingDateBeforeNow_thenThrowInvalidItemGroupInformationException(){
        ItemGroup itemGroup = new ItemGroup("userId",5);
        Assertions.assertThatExceptionOfType(InvalidItemGroupInformationException.class)
                .isThrownBy(() -> itemGroup.setShippingDate(LocalDate.now().minusDays(1)));
    }
}