package com.example.jpashop.service;

import com.example.jpashop.domain.item.Book;
import com.example.jpashop.domain.item.Item;
import com.example.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void save(Item item) {
        itemRepository.save(item);
    }
    public List<Item> findAll() {
        return itemRepository.findAll();
    }
    public List<Item> findByName(String name) {
        return itemRepository.findByName(name);
    }
    public Item findById(Long id) {
        return itemRepository.findOne(id);
    }

    @Transactional
    public void updateItem(Long id, Book book) {
        Item item = itemRepository.findOne(id);
        modelMapper.map(book, item);
    }
}
