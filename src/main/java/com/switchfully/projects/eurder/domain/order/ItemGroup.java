package com.switchfully.projects.eurder.domain.order;

import com.switchfully.projects.eurder.domain.exception.InvalidItemGroupInformationException;

import java.time.LocalDate;
import java.util.UUID;

public class ItemGroup {

    private final String itemGroupId;
    private final String itemId;
    private final int amount;
    private LocalDate shippingDate;

    public ItemGroup(String itemId, int amount) {
        this.itemGroupId = UUID.randomUUID().toString();
        this.itemId = itemId;
        validateAmount(amount);
        this.amount = amount;
    }

    public String getItemGroupId() {
        return itemGroupId;
    }

    public String getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
    }

    private void validateAmount(int amount){
        if(amount <= 0){
            throw new InvalidItemGroupInformationException("The amount " + amount +
                    " in an item group has to strictly positive");
        }
    }
}
