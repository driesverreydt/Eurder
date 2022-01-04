package com.switchfully.projects.eurder.api.mapper;

import com.switchfully.projects.eurder.api.dto.OrderDto;
import com.switchfully.projects.eurder.domain.order.ItemGroup;
import com.switchfully.projects.eurder.domain.order.Order;
import com.switchfully.projects.eurder.domain.user.User;
import com.switchfully.projects.eurder.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class OrderMapper {

    private final ItemGroupMapper itemGroupMapper;
    private final UserService userService;

    public OrderMapper(ItemGroupMapper itemGroupMapper, UserService userService) {
        this.itemGroupMapper = itemGroupMapper;
        this.userService = userService;
    }

}
