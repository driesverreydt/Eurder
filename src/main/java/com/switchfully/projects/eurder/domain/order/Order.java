package com.switchfully.projects.eurder.domain.order;

import java.util.Collection;
import java.util.UUID;

public class Order {

    private final String orderId;
    private final String userId;
    private final Collection<ItemGroup> itemGroupCollection;

    public Order(String userId, Collection<ItemGroup> itemGroupCollection) {
        this.orderId = UUID.randomUUID().toString();
        this.userId = userId;
        this.itemGroupCollection = itemGroupCollection;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public Collection<ItemGroup> getItemGroupCollection() {
        return itemGroupCollection;
    }

    public double getTotalOrderPrice() {
        return itemGroupCollection.stream()
                .map(itemGroup -> itemGroup.getAmount() * itemGroup.getPriceSnapshot())
                .reduce(0.0, Double::sum);
    }
}
