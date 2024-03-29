package com.switchfully.projects.eurder.domain.item;

import com.switchfully.projects.eurder.domain.exception.InvalidItemInformationException;
import com.switchfully.projects.eurder.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @Column(name = "item_id")
    private UUID itemId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "amount")
    private int amount;

    @Transient
    private final Logger logger = LoggerFactory.getLogger(User.class);

    public Item(String name, String description, double price, int amount) {
        itemInformationValidation(name, description, price, amount);
        this.itemId = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    private Item() {
    }

    public UUID getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (amount < 0) {
            logger.error("For an item amount can not be less than 0");
            throw new InvalidItemInformationException("For an item amount can not be less than 0");
        }
        this.amount = amount;
    }

    private void itemInformationValidation(String name, String description, double price, int amount) {
        if (name == null) {
            logger.error("At item creation name was null");
            throw new InvalidItemInformationException("An item requires a name");
        }
        if (description == null) {
            logger.error("At item creation description was null");
            throw new InvalidItemInformationException("An item requires a description");
        }
        if (price <= 0) {
            logger.error("At item creation price was not greater than 0");
            throw new InvalidItemInformationException("An item requires a price greater than 0");
        }
        if (amount <= 0) {
            logger.error("At item creation amount was not greater than 0");
            throw new InvalidItemInformationException("An item requires an amount greater than 0");
        }
    }
}
