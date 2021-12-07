package com.switchfully.projects.eurder.service;

import java.time.LocalDate;

public class ShippingDateCalculatorOutOfStock implements ShippingDateCalculator {

    private final static int NUMBER_OF_SHIPPING_DAYS_OUT_OF_STOCK = 7;

    @Override
    public LocalDate calculateShippingDate() {
        return LocalDate.now().plusDays(NUMBER_OF_SHIPPING_DAYS_OUT_OF_STOCK);
    }
}
