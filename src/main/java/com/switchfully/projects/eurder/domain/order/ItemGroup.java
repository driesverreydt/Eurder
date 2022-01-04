package com.switchfully.projects.eurder.domain.order;

import com.switchfully.projects.eurder.domain.exception.InvalidItemGroupInformationException;
import com.switchfully.projects.eurder.domain.item.Item;
import com.switchfully.projects.eurder.service.shippingdatecalculator.ShippingDateCalculator;
import com.switchfully.projects.eurder.service.shippingdatecalculator.ShippingDateCalculatorInStock;
import com.switchfully.projects.eurder.service.shippingdatecalculator.ShippingDateCalculatorOutOfStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "item_groups")
public class ItemGroup {

    @Id
    @Column(name = "item_group_id")
    private UUID itemGroupId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "price_snapshot")
    private double priceSnapshot;

    @Column(name = "amount")
    private int amount;

    @Column(name = "shipping_date")
    private LocalDate shippingDate;

    @Transient
    private final Logger logger = LoggerFactory.getLogger(ItemGroup.class);

    public ItemGroup(Item item, int amount) {
        validateItemGroupInformation(item, amount);
        this.itemGroupId = UUID.randomUUID();
        this.item = item;
        this.amount = amount;
        setPriceSnapshot();
        setShippingDate();
        adjustStock();
    }

    private ItemGroup() {
    }

    public UUID getItemGroupId() {
        return itemGroupId;
    }

    public Item getItem() {
        return item;
    }

    public double getPriceSnapshot() {
        return priceSnapshot;
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

    private void validateItemGroupInformation(Item item, int amount) {
        if (item == null) {
            logger.error("For an item group the itemId of the item it references has to be present");
            throw new InvalidItemGroupInformationException("The itemId in an item group cannot be null");
        }
        if (amount <= 0) {
            logger.error("For an item group the amount has to be strictly positive upon creation");
            throw new InvalidItemGroupInformationException("The amount " + amount +
                    " in an item group has to strictly positive");
        }
    }

    private void setPriceSnapshot() {
        this.priceSnapshot = item.getPrice();
    }

    private void setShippingDate() {
        boolean enoughInStock = item.getAmount() >= amount;

        ShippingDateCalculator shippingDateCalculator;
        if (enoughInStock) {
            shippingDateCalculator = new ShippingDateCalculatorInStock();
        } else {
            shippingDateCalculator = new ShippingDateCalculatorOutOfStock();
        }
        this.shippingDate = shippingDateCalculator.calculateShippingDate();
    }

    private void adjustStock() {
        int newAmount = item.getAmount() - amount;
        if (newAmount >= 0) {
            item.setAmount(newAmount);
        }
    }
}
