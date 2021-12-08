package com.switchfully.projects.eurder.domain.order;

import com.switchfully.projects.eurder.domain.exception.InvalidItemGroupInformationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.UUID;

public class ItemGroup {

    private final String itemGroupId;
    private final String itemId;
    private double priceSnapshot;
    private final int amount;
    private LocalDate shippingDate;
    private final Logger logger = LoggerFactory.getLogger(ItemGroup.class);

    public ItemGroup(String itemId, int amount) {
        validateItemGroupInformation(itemId, priceSnapshot, amount);
        this.itemGroupId = UUID.randomUUID().toString();
        this.itemId = itemId;
        this.amount = amount;
    }

    public String getItemGroupId() {
        return itemGroupId;
    }

    public String getItemId() {
        return itemId;
    }

    public double getPriceSnapshot() {
        return priceSnapshot;
    }

    public void setPriceSnapshot(double priceSnapshot) {
        if (priceSnapshot <= 0) {
            logger.error("For an item group snapshot price can not be less than 0");
            throw new InvalidItemGroupInformationException("The price " + priceSnapshot +
                    " in an item group has to strictly positive");
        }
        this.priceSnapshot = priceSnapshot;
    }

    public int getAmount() {
        return amount;
    }

    public double getItemGroupPrice() {
        return amount * priceSnapshot;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(LocalDate shippingDate) {
        if (shippingDate.isBefore(LocalDate.now())) {
            logger.error("For an item group the shipping date can not be set before today");
            throw new InvalidItemGroupInformationException("The shippingDate " + shippingDate +
                    " in an item group cannot be before today");
        }
        this.shippingDate = shippingDate;
    }

    private void validateItemGroupInformation(String itemId, double priceSnapshot, int amount) {
        if (itemId == null) {
            logger.error("For an item group the itemId of the item it references has to be present");
            throw new InvalidItemGroupInformationException("The itemId in an item group cannot be null");
        }
        if (amount <= 0) {
            logger.error("For an item group the amount has to be strictly positive upon creation");
            throw new InvalidItemGroupInformationException("The amount " + amount +
                    " in an item group has to strictly positive");
        }
    }
}
