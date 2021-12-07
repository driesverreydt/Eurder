package com.switchfully.projects.eurder.api.mapper;

import com.switchfully.projects.eurder.api.dto.ItemDto;
import com.switchfully.projects.eurder.domain.item.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public Item mapItemDtoToItem(ItemDto itemDto) {
        return new Item(itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getPrice(),
                itemDto.getAmount());
    }

    public ItemDto mapItemToItemDto(Item item) {
        return new ItemDto.ItemDtoBuilder()
                .setItemId(item.getItemId())
                .setName(item.getName())
                .setDescription(item.getDescription())
                .setPrice(item.getPrice())
                .setAmount(item.getAmount())
                .build();
    }
}
