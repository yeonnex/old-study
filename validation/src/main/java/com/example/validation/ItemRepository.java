package com.example.validation;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepository {
    private static int count;
    List<Item> store = new ArrayList<>();

    public Item save(Item item) {
        item.setId(++count);
        store.add(item);
        return item;
    }

    public Item findById(Integer id) {
        return store.get(--id);
    }
}
