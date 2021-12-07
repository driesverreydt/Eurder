package com.switchfully.projects.eurder.repository;

import com.switchfully.projects.eurder.domain.item.Item;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public class ItemRepository {

    private final Collection<Item> itemCollection;

    public ItemRepository() {
        itemCollection = new ArrayList<>();
    }

    public Item addItem(Item item){
        itemCollection.add(item);
        return item;
    }

    public Item removeItem(Item item){
        itemCollection.remove(item);
        return item;
    }

    public Collection<Item> getAllItems(){
        return itemCollection;
    }
}
