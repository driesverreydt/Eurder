package com.switchfully.projects.eurder.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.UUID;

@JsonDeserialize(builder = ItemDto.ItemDtoBuilder.class)
public class ItemDto {

    private final UUID itemId;
    private final String name;
    private final String description;
    private final double price;
    private final int amount;

    private ItemDto(ItemDtoBuilder itemDtoBuilder) {
        this.itemId = itemDtoBuilder.itemId;
        this.name = itemDtoBuilder.name;
        this.description = itemDtoBuilder.description;
        this.price = itemDtoBuilder.price;
        this.amount = itemDtoBuilder.amount;
    }

    public UUID getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class ItemDtoBuilder {
        private UUID itemId;
        private String name;
        private String description;
        private double price;
        private int amount;

        public ItemDtoBuilder setItemId(UUID itemId) {
            this.itemId = itemId;
            return this;
        }

        public ItemDtoBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ItemDtoBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ItemDtoBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public ItemDtoBuilder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public ItemDto build() {
            return new ItemDto(this);
        }
    }
}
