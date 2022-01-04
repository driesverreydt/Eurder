package com.switchfully.projects.eurder.service;

import com.switchfully.projects.eurder.api.dto.ItemGroupDto;
import com.switchfully.projects.eurder.api.dto.OrderDto;
import com.switchfully.projects.eurder.api.mapper.OrderMapper;
import com.switchfully.projects.eurder.domain.exception.InvalidItemGroupInformationException;
import com.switchfully.projects.eurder.domain.exception.InvalidOrderInformationException;
import com.switchfully.projects.eurder.domain.item.Item;
import com.switchfully.projects.eurder.domain.order.ItemGroup;
import com.switchfully.projects.eurder.domain.order.Order;
import com.switchfully.projects.eurder.domain.user.User;
import com.switchfully.projects.eurder.repository.OrderRepositoryInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepositoryInterface orderRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    public OrderService(OrderRepositoryInterface orderRepository, UserService userService, ItemService itemService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.itemService = itemService;
    }

    public double saveOrderDto(OrderDto orderDto) {
        validateOrderInformation(orderDto);

        Order order = createOrder(orderDto);

        Order savedOrder = orderRepository.save(order);

        return savedOrder.getTotalOrderPrice();
    }

    private void validateOrderInformation(OrderDto orderDto){
        if (orderDto.getUserId() == null) {
            logger.error("The user in the order can not be null");
            throw new InvalidOrderInformationException("The user in the order can not be null");
        }
        doesUserExist(orderDto.getUserId());
        if (orderDto.getItemGroupDtoCollection() == null) {
            logger.error("The item group collection in the order can not be null");
            throw new InvalidOrderInformationException("The item group collection in the order can not be null");
        }
        if (orderDto.getItemGroupDtoCollection().isEmpty()) {
            logger.error("The item group collection in the order can not be empty");
            throw new InvalidOrderInformationException("The item group collection in the order can not be empty");
        }
        for (ItemGroupDto itemGroupDto : orderDto.getItemGroupDtoCollection()) {
            if (itemGroupDto.getItemId() == null) {
                logger.error("For an item group the itemId of the item it references has to be present");
                throw new InvalidItemGroupInformationException("The itemId in an item group cannot be null");
            }
        }
        doItemsExist(orderDto.getItemGroupDtoCollection());
    }

    private void doesUserExist(UUID userId) {
        if (userService.getUsersByUserId(userId).isEmpty()) {
            logger.error("The userId " + userId + " in the order is not in the system");
            throw new InvalidOrderInformationException("The userId " + userId + " in the order is not in the system");
        }
    }

    private void doItemsExist(Collection<ItemGroupDto> itemGroupDtoCollection) {
        for (ItemGroupDto itemGroupDto : itemGroupDtoCollection) {
            if (itemService.getItemsById(itemGroupDto.getItemId()).isEmpty()) {
                logger.error("The itemId " + itemGroupDto.getItemId() + " in the order is not in the system");
                throw new InvalidOrderInformationException("The itemId " + itemGroupDto.getItemId() + " in the order is not in the system");
            }
        }
    }

    private Order createOrder(OrderDto orderDto){
        User user = userService.getUserById(orderDto.getUserId());
        List<ItemGroup> itemGroups = new ArrayList<>();
        for(ItemGroupDto itemGroupDto : orderDto.getItemGroupDtoCollection()){
            Item item = itemService.getItemById(itemGroupDto.getItemId());
            ItemGroup itemGroup = new ItemGroup(item,itemGroupDto.getAmount());
            itemGroups.add(itemGroup);
        }
        return new Order(user, itemGroups);
    }
}
