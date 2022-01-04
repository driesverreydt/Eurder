package com.switchfully.projects.eurder.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDate;
import java.util.UUID;

@JsonDeserialize(builder = ItemGroupDto.ItemGroupDtoBuilder.class)
public class ItemGroupDto {

    private final UUID itemGroupId;
    private final UUID itemId;
    private final double priceSnapshot;
    private final int amount;
    private final LocalDate shippingDate;

    private ItemGroupDto(ItemGroupDtoBuilder itemGroupDtoBuilder) {
        this.itemGroupId = itemGroupDtoBuilder.itemGroupId;
        this.itemId = itemGroupDtoBuilder.itemId;
        this.priceSnapshot = itemGroupDtoBuilder.priceSnapshot;
        this.amount = itemGroupDtoBuilder.amount;
        this.shippingDate = itemGroupDtoBuilder.shippingDate;
    }

    public UUID getItemGroupId() {
        return itemGroupId;
    }

    public UUID getItemId() {
        return itemId;
    }

    public double getPriceSnapshot() {
        return priceSnapshot;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class ItemGroupDtoBuilder {
        private UUID itemGroupId;
        private UUID itemId;
        private double priceSnapshot;
        private int amount;
        private LocalDate shippingDate;

        public ItemGroupDtoBuilder setItemGroupId(UUID itemGroupId) {
            this.itemGroupId = itemGroupId;
            return this;
        }

        public ItemGroupDtoBuilder setItemId(UUID itemId) {
            this.itemId = itemId;
            return this;
        }

        public ItemGroupDtoBuilder setPriceSnapshot(double priceSnapshot) {
            this.priceSnapshot = priceSnapshot;
            return this;
        }

        public ItemGroupDtoBuilder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public ItemGroupDtoBuilder setShippingDate(LocalDate shippingDate) {
            this.shippingDate = shippingDate;
            return this;
        }

        public ItemGroupDto build() {
            return new ItemGroupDto(this);
        }
    }
}
