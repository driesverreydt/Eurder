package com.switchfully.projects.eurder.domain.order;

import com.switchfully.projects.eurder.domain.exception.InvalidOrderInformationException;
import com.switchfully.projects.eurder.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    private UUID orderId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private Collection<ItemGroup> itemGroupCollection;

    @Transient
    private final Logger logger = LoggerFactory.getLogger(Order.class);

    public Order(User user, Collection<ItemGroup> itemGroupCollection) {
        validateOrderInformation(user, itemGroupCollection);
        this.orderId = UUID.randomUUID();
        this.user = user;
        this.itemGroupCollection = itemGroupCollection;
    }

    private Order() {
    }

    public UUID getOrderId() {
        return orderId;
    }

    public User getUser() {
        return user;
    }

    public Collection<ItemGroup> getItemGroupCollection() {
        return itemGroupCollection;
    }

    public double getTotalOrderPrice() {
        return itemGroupCollection.stream()
                .map(itemGroup -> itemGroup.getAmount() * itemGroup.getPriceSnapshot())
                .reduce(0.0, Double::sum);
    }

    private void validateOrderInformation(User user, Collection<ItemGroup> itemGroupCollection) {
        if (user == null) {
            logger.error("For an order the user referencing the customer has to be present");
            throw new InvalidOrderInformationException("For an order the user referencing the customer " +
                    "has to be present");
        }
        if (itemGroupCollection == null) {
            logger.error("For an order the itemGroupCollection referencing the items to be ordered " +
                    "has to be present");
            throw new InvalidOrderInformationException("For an order the itemGroupCollection " +
                    "referencing the items to be ordered has to be present");
        }
        if (itemGroupCollection.isEmpty()) {
            logger.error("For an order the itemGroupCollection referencing the items to be ordered " +
                    "has to contain at least one itemGroup");
            throw new InvalidOrderInformationException("For an order the itemGroupCollection referencing " +
                    "the items to be ordered has to contain at least one itemGroup");
        }
    }
}
