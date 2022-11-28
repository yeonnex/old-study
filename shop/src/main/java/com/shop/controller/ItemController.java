package com.shop.controller;

import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemSearchDto;
import com.shop.entity.Item;
import com.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ItemController {
    private final ItemService itemService;

    /**
     * 상품 등록 페이지로 이동한다
     * @param model
     * @return
     */
    @GetMapping("/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "/item/itemForm";
    }

    /**
     * 신규 상품을 등록한다
     * @param form
     * @param bindingResult
     * @param model
     * @param itemImgFileList
     * @return
     */
    @PostMapping("/item/new")
    public String itemNew(@Valid ItemFormDto form,
                          BindingResult bindingResult,
                          Model model,
                          @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList){
        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }
        if (itemImgFileList.get(0).isEmpty() && form.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품이미지는 필수 입력값입니다.");
            return "item/itemForm";
        }
        try {
            itemService.saveItem(form, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        return "redirect:/main";
    }

    /**
     * 상품 상세를 조회한다
     * @param itemId
     * @param model
     * @return
     */
    @GetMapping("/item/{itemId}")
    public String itemDetail(@PathVariable("itemId")Long itemId, Model model) {
        try {
            ItemFormDto itemFormDto = itemService.getItemDetail(itemId);
            model.addAttribute("itemFormDto", itemFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다");
            model.addAttribute("itemFormDto", new ItemFormDto());
        }
        return "item/itemForm";
    }

    @PostMapping("/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto form,
                             BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }
        if (itemImgFileList.get(0).isEmpty() && form.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품이미지는 필수 입력값입니다.");
            return "item/itemForm";
        }
        try {
            itemService.updateItem(form, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정중 에러가 발생했습니다.");
            return "item/itemForm";
        }

        return "redirect:/main";
    }

    /**
     * 상품 목록을 반환한다. 페이징 가능
     */
    @GetMapping(value = {"/items", "/items/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto,
                             @PathVariable("page") Optional<Integer> page,
                             Model model){
        PageRequest pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);// 한 페이지 당 아이템 세개씩 보여준다
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);
        return "item/itemMng";
    }

}
