package com.switchfully.projects.eurder.api.mapper;

import com.switchfully.projects.eurder.api.dto.ItemGroupDto;
import com.switchfully.projects.eurder.domain.order.ItemGroup;
import org.springframework.stereotype.Component;

@Component
public class ItemGroupMapper {

    public ItemGroup mapItemGroupDtoToItemGroup(ItemGroupDto itemGroupDto) {
        return new ItemGroup(itemGroupDto.getItemId(),
                itemGroupDto.getAmount());
    }
}
