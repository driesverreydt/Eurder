package com.switchfully.projects.eurder.api.mapper;

import com.switchfully.projects.eurder.api.dto.OrderDto;
import com.switchfully.projects.eurder.domain.order.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order mapOrderDtoToOrder(OrderDto orderDto) {
        return new Order(orderDto.getUserId(),
                orderDto.getItemGroupCollection());
    }
}
