package com.switchfully.projects.eurder.service;

import com.switchfully.projects.eurder.domain.exception.InvalidOrderInformationException;
import com.switchfully.projects.eurder.domain.item.Item;
import com.switchfully.projects.eurder.domain.order.ItemGroup;
import com.switchfully.projects.eurder.domain.order.Order;
import com.switchfully.projects.eurder.repository.OrderRepository;
import com.switchfully.projects.eurder.service.shippingdatecalculator.ShippingDateCalculator;
import com.switchfully.projects.eurder.service.shippingdatecalculator.ShippingDateCalculatorInStock;
import com.switchfully.projects.eurder.service.shippingdatecalculator.ShippingDateCalculatorOutOfStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    public OrderService(OrderRepository orderRepository, UserService userService, ItemService itemService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.itemService = itemService;
    }

    public double saveOrder(Order order) {
        doesUserExist(order.getUserId());
        doItemsExist(order.getItemGroupCollection());
        setPriceSnapShots(order);
        setShippingOrders(order);
        adjustStock(order);
        orderRepository.addOrder(order);
        return order.getTotalOrderPrice();
    }

    private void doesUserExist(String userId) {
        if (userService.getUsersByUserId(userId).isEmpty()) {
            logger.error("The userId " + userId + " in the order is not in the system");
            throw new InvalidOrderInformationException("The userId " + userId + " in the order is not in the system");
        }
    }

    private void doItemsExist(Collection<ItemGroup> itemGroupCollection) {
        for (ItemGroup itemGroup : itemGroupCollection) {
            if (itemService.getItemsById(itemGroup.getItemId()).isEmpty()) {
                logger.error("The itemId " + itemGroup.getItemId() + " in the order is not in the system");
                throw new InvalidOrderInformationException("The itemId " + itemGroup.getItemId() + " in the order is not in the system");
            }
        }
    }

    private void setPriceSnapShots(Order order) {
        for (ItemGroup itemGroup : order.getItemGroupCollection()) {
            Item item = itemService.getItemsById(itemGroup.getItemId()).iterator().next();
            itemGroup.setPriceSnapshot(item.getPrice());
        }
    }

    private void setShippingOrders(Order order) {
        for (ItemGroup itemGroup : order.getItemGroupCollection()) {
            Item item = itemService.getItemsById(itemGroup.getItemId()).iterator().next();
            boolean enoughInStock = item.getAmount() >= itemGroup.getAmount();

            ShippingDateCalculator shippingDateCalculator;
            if (enoughInStock) {
                shippingDateCalculator = new ShippingDateCalculatorInStock();
            } else {
                shippingDateCalculator = new ShippingDateCalculatorOutOfStock();
            }
            itemGroup.setShippingDate(shippingDateCalculator.calculateShippingDate());
        }
    }

    private void adjustStock(Order order) {
        for (ItemGroup itemGroup : order.getItemGroupCollection()) {
            Item item = itemService.getItemsById(itemGroup.getItemId()).iterator().next();
            int newAmount = item.getAmount()-itemGroup.getAmount();
            if(newAmount >= 0) {
                item.setAmount(newAmount);
            }
        }
    }


}
