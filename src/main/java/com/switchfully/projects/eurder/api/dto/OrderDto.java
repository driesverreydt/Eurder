package com.switchfully.projects.eurder.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Collection;
import java.util.UUID;

@JsonDeserialize(builder = OrderDto.OrderDtoBuilder.class)
public class OrderDto {

    private final UUID orderId;
    private final UUID userId;
    private final Collection<ItemGroupDto> itemGroupDtoCollection;

    private OrderDto(OrderDtoBuilder orderDtoBuilder) {
        this.orderId = orderDtoBuilder.orderId;
        this.userId = orderDtoBuilder.userId;
        this.itemGroupDtoCollection = orderDtoBuilder.itemGroupDtoCollection;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getUserId() {
        return userId;
    }

    public Collection<ItemGroupDto> getItemGroupDtoCollection() {
        return itemGroupDtoCollection;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class OrderDtoBuilder {
        private UUID orderId;
        private UUID userId;
        private Collection<ItemGroupDto> itemGroupDtoCollection;

        public OrderDtoBuilder setOrderId(UUID orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderDtoBuilder setUserId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public OrderDtoBuilder setItemGroupDtoCollection(Collection<ItemGroupDto> itemGroupDtoCollection) {
            this.itemGroupDtoCollection = itemGroupDtoCollection;
            return this;
        }

        public OrderDto build() {
            return new OrderDto(this);
        }
    }
}
