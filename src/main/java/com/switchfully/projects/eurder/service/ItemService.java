package com.switchfully.projects.eurder.service;

import com.switchfully.projects.eurder.api.dto.ItemDto;
import com.switchfully.projects.eurder.api.mapper.ItemMapper;
import com.switchfully.projects.eurder.domain.exception.NoSuchCustomerException;
import com.switchfully.projects.eurder.domain.item.Item;
import com.switchfully.projects.eurder.repository.ItemRepositoryInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ItemService {

    private final ItemRepositoryInterface itemRepository;
    private final ItemMapper itemMapper;
    private final Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    public ItemService(ItemRepositoryInterface itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public Collection<ItemDto> getItemDtosById(UUID itemId) {
        return itemRepository.findAll().stream()
                .filter(item -> item.getItemId().equals(itemId))
                .map(itemMapper::mapItemToItemDto)
                .toList();
    }

    public ItemDto saveItemDto(ItemDto itemDto) {
        Item itemToSave = itemMapper.mapItemDtoToItem(itemDto);
        Item savedItem = itemRepository.save(itemToSave);
        ItemDto savedItemDto = itemMapper.mapItemToItemDto(savedItem);
        return savedItemDto;
    }

    public Collection<Item> getItemsById(UUID itemId) {
        return itemRepository.findAll().stream()
                .filter(item -> item.getItemId().equals(itemId))
                .toList();
    }

    public Item getItemById(UUID itemId) {
        Item item;
        try {
            item = getItemsById(itemId).iterator().next();
        } catch (NoSuchElementException ex) {
            logger.error("Item with id " + itemId + "could not be found");
            throw new NoSuchCustomerException("Item with id " + itemId + " could not be found");
        }
        return item;
    }

}
