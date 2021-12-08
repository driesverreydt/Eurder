package com.switchfully.projects.eurder.api.controller;

import com.switchfully.projects.eurder.api.dto.OrderDto;
import com.switchfully.projects.eurder.api.mapper.OrderMapper;
import com.switchfully.projects.eurder.domain.order.Order;
import com.switchfully.projects.eurder.security.AuthorizationService;
import com.switchfully.projects.eurder.security.EurderFeature;
import com.switchfully.projects.eurder.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final AuthorizationService authorizationService;
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper, AuthorizationService authorizationService) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.authorizationService = authorizationService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public double createOrder(@RequestBody OrderDto orderDto, @RequestHeader(required = false) String authorization) {
        logger.info("Add order method called");
        authorizationService.authorize(EurderFeature.ORDER_CREATE, authorization);
        Order order = orderMapper.mapOrderDtoToOrder(orderDto);
        double totalOrderPrice = orderService.saveOrder(order);
        logger.info("Add order method successfully finished");
        return totalOrderPrice;
    }
}
