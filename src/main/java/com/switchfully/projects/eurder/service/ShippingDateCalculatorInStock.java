package com.switchfully.projects.eurder.service;

import java.time.LocalDate;

public class ShippingDateCalculatorInStock implements ShippingDateCalculator {

    private final static int NUMBER_OF_SHIPPING_DAYS_IN_STOCK = 1;

    @Override
    public LocalDate calculateShippingDate() {
        return LocalDate.now().plusDays(NUMBER_OF_SHIPPING_DAYS_IN_STOCK);
    }
}
