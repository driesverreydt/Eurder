package com.switchfully.projects.eurder.repository;

import com.switchfully.projects.eurder.domain.order.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public class OrderRepository {

    private final Collection<Order> orderCollection;

    public OrderRepository() {
        this.orderCollection = new ArrayList<>();
    }

    public Collection<Order> getAllOrders() {
        return orderCollection;
    }

    public Order addOrder(Order order) {
        orderCollection.add(order);
        return order;
    }

    public Order removeOrder(Order order) {
        orderCollection.remove(order);
        return order;
    }
}
