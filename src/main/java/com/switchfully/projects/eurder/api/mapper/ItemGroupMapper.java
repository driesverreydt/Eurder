package com.switchfully.projects.eurder.api.mapper;

import com.switchfully.projects.eurder.api.dto.ItemGroupDto;
import com.switchfully.projects.eurder.domain.item.Item;
import com.switchfully.projects.eurder.domain.order.ItemGroup;
import com.switchfully.projects.eurder.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemGroupMapper {

    private final ItemService itemService;

    @Autowired
    public ItemGroupMapper(ItemService itemService) {
        this.itemService = itemService;
    }

}
