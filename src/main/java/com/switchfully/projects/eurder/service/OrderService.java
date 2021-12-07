package com.switchfully.projects.eurder.service;

import com.switchfully.projects.eurder.domain.exception.InvalidOrderInformationException;
import com.switchfully.projects.eurder.domain.item.Item;
import com.switchfully.projects.eurder.domain.order.ItemGroup;
import com.switchfully.projects.eurder.domain.order.Order;
import com.switchfully.projects.eurder.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ItemService itemService;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserService userService, ItemService itemService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.itemService = itemService;
    }

    public double saveOrder(Order order){
        doesUserExist(order.getUserId());
        doItemsExist(order.getItemGroupCollection());
        setShippingOrdersAndAdjustStock(order);
        orderRepository.addOrder(order);
        return order.getTotalPrice();
    }

    private void doesUserExist(String userId){
        if(userService.getUsersByUserId(userId).isEmpty()){
            throw new InvalidOrderInformationException("The userId " + userId + " in the order is not in the system");
        }
    }

    private void doItemsExist(Collection<ItemGroup> itemGroupCollection){
        for(ItemGroup itemGroup : itemGroupCollection){
            if(itemService.getItemsById(itemGroup.getItemId()).isEmpty()){
                throw new InvalidOrderInformationException("The itemId " + itemGroup.getItemId() + " in the order is not in the system");
            }
        }
    }

    //TODO refactor method
    private void setShippingOrdersAndAdjustStock(Order order) {
        Collection<ItemGroup> oldItemGroup = order.getItemGroupCollection();
        for(ItemGroup itemGroup : oldItemGroup){
            Item item = itemService.getItemsById(itemGroup.getItemId()).iterator().next();
            boolean inStock = itemService.reduceStockOfItem(item,itemGroup.getAmount());

            ShippingDateCalculator shippingDateCalculator;
            if(inStock){
                shippingDateCalculator = new ShippingDateCalculatorInStock();
            } else {
                shippingDateCalculator = new ShippingDateCalculatorOutOfStock();
            }
            itemGroup.setShippingDate(shippingDateCalculator.calculateShippingDate());
        }
    }
}
