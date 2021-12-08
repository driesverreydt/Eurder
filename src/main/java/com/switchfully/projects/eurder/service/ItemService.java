package com.switchfully.projects.eurder.service;

import com.switchfully.projects.eurder.domain.item.Item;
import com.switchfully.projects.eurder.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Collection<Item> getItemsById(String itemId) {
        return itemRepository.getAllItems().stream()
                .filter(item -> item.getItemId().equals(itemId))
                .toList();
    }

    public Item saveItem(Item item) {
        return itemRepository.addItem(item);
    }

    //TODO move this into item?
    public void reduceStockOfItem(Item item, int amount) {
        int newAmount = item.getAmount() - amount;
        if (newAmount >= 0) {
            item.setAmount(newAmount);
        }
    }
}
