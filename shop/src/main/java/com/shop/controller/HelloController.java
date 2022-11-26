package com.shop.controller;

import com.shop.dto.ItemDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@Slf4j
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("")
    public String hello(Model model) {
        ArrayList<ItemDto> itemDtoList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            ItemDto dto = new ItemDto();
            dto.setItemNm("아이템 " + i);
            dto.setPrice(1000 + i);
            dto.setItemDetail("상품상세 " + i);
            itemDtoList.add(dto);
        }
        model.addAttribute("itemDtoList", itemDtoList);
        return "hello";
    }

    @GetMapping("/items")
    public String allUsers( Model model,
                            @Param("itemNm") String itemNm,
                            @Param("itemDetail") String itemDetail) {
        log.info("아이템 이릉: {}", itemNm);
        log.info("아이템 상세: {}", itemDetail);
        model.addAttribute("itemNm", itemNm);
        model.addAttribute("itemDetail", itemDetail);
        return "hi";
    }

    @GetMapping("/mylayout")
    public String myLayout() {
        return "mylayout";
    }


}
