package com.switchfully.projects.eurder.domain.order;

import com.switchfully.projects.eurder.domain.exception.InvalidOrderInformationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.UUID;

public class Order {

    private final String orderId;
    private final String userId;
    private final Collection<ItemGroup> itemGroupCollection;
    private final Logger logger = LoggerFactory.getLogger(Order.class);

    public Order(String userId, Collection<ItemGroup> itemGroupCollection) {
        validateOrderInformation(userId,itemGroupCollection);
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

    private void validateOrderInformation(String userId, Collection<ItemGroup> itemGroupCollection) {
        if(userId == null){
            logger.error("For an order the userId referencing the customer has to be present");
            throw new InvalidOrderInformationException("For an order the userId referencing the customer " +
                    "has to be present");
        }
        if(itemGroupCollection == null){
            logger.error("For an order the itemGroupCollection referencing the items to be ordered " +
                    "has to be present");
            throw new InvalidOrderInformationException("For an order the itemGroupCollection " +
                    "referencing the items to be ordered has to be present");
        }
        if(itemGroupCollection.isEmpty()){
            logger.error("For an order the itemGroupCollection referencing the items to be ordered " +
                    "has to contain at least one itemGroup");
            throw new InvalidOrderInformationException("For an order the itemGroupCollection referencing " +
                    "the items to be ordered has to contain at least one itemGroup");
        }
    }
}
