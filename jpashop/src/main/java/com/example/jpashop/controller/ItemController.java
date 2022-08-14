package com.example.jpashop.controller;

import com.example.jpashop.domain.item.Book;
import com.example.jpashop.domain.item.Item;
import com.example.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ModelMapper modelMapper;
    private final ItemService itemService;

    @GetMapping("/new")
    public String itemForm(Model model) {
        model.addAttribute("itemForm", new BookForm());
        return "item/itemForm";
    }

    @PostMapping("/new")
    public String itemSubmit(BookForm bookForm) {
        Item item = modelMapper.map(bookForm, Book.class);
        itemService.save(item);
        return "redirect:/items";
    }

    @GetMapping("")
    public String itemList(Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        return "item/itemList";

    }

    @GetMapping("/{id}/edit")
    public String itemUpdateForm(Model model, @PathVariable Long id) {
        Item item = itemService.findById(id);
        model.addAttribute("itemForm", item);
        return "item/itemUpdateForm";
    }


    @PostMapping("/{id}/edit")
    public String itemUpdate(BookForm bookForm, @PathVariable Long id) {
        Book book = modelMapper.map(bookForm, Book.class);
        itemService.updateItem(id, book);
        return "redirect:/items";
    }
}
