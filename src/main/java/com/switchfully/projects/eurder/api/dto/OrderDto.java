package com.switchfully.projects.eurder.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.switchfully.projects.eurder.domain.order.ItemGroup;

import java.util.Collection;

@JsonDeserialize(builder = OrderDto.OrderDtoBuilder.class)
public class OrderDto {

    private final String orderId;
    private final String userId;
    private final Collection<ItemGroup> itemGroupCollection;

    private OrderDto(OrderDtoBuilder orderDtoBuilder) {
        this.orderId = orderDtoBuilder.orderId;
        this.userId = orderDtoBuilder.userId;
        this.itemGroupCollection = orderDtoBuilder.itemGroupCollection;
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

    @JsonPOJOBuilder(withPrefix = "set")
    public static class OrderDtoBuilder {
        private String orderId;
        private String userId;
        private Collection<ItemGroup> itemGroupCollection;

        public OrderDtoBuilder setOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderDtoBuilder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public OrderDtoBuilder setItemGroupCollection(Collection<ItemGroup> itemGroupCollection) {
            this.itemGroupCollection = itemGroupCollection;
            return this;
        }

        public OrderDto build() {
            return new OrderDto(this);
        }
    }
}
