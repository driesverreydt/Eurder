package com.switchfully.projects.eurder.api.mapper;

import com.switchfully.projects.eurder.api.dto.ItemGroupDto;
import com.switchfully.projects.eurder.api.dto.OrderDto;
import com.switchfully.projects.eurder.domain.order.ItemGroup;
import com.switchfully.projects.eurder.domain.order.Order;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class OrderMapper {

    private final ItemGroupMapper itemGroupMapper;

    public OrderMapper(ItemGroupMapper itemGroupMapper) {
        this.itemGroupMapper = itemGroupMapper;
    }

    public Order mapOrderDtoToOrder(OrderDto orderDto) {
        Collection<ItemGroup> itemGroupCollection = orderDto.getItemGroupDtoCollection().stream()
                .map(itemGroupMapper::mapItemGroupDtoToItemGroup).toList();
        return new Order(orderDto.getUserId(),
                itemGroupCollection);
    }

    public OrderDto mapOrderToOrderDto(Order order) {
        Collection<ItemGroupDto> itemGroupDtoCollection = order.getItemGroupCollection().stream()
                .map(itemGroupMapper::mapItemGroupToItemGroupDto).toList();
        return new OrderDto.OrderDtoBuilder()
                .setOrderId(order.getOrderId())
                .setUserId(order.getUserId())
                .setItemGroupDtoCollection(itemGroupDtoCollection)
                .build();
    }

}
