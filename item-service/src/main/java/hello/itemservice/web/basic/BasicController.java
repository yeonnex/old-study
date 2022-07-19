package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.ExitMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicController {
    private final ItemRepository itemRepository;

    /**
      상품 등록 폼
     */
    @GetMapping("/add")
    public String addItemForm() {
        return "basic/addForm";
    }

    /**
      상품 저장 V1
     */
//    @PostMapping("/add")
    public String saveItemV1(@RequestParam String itemName,
                           @RequestParam int price,
                           @RequestParam int quantity,
                           Model model) {
        Item item = new Item(itemName, price, quantity);
        Item save = itemRepository.save(item);
        model.addAttribute("item", save);
        return "basic/item";
    }

    /**
     * 상품 저장 V2
     */
    //@PostMapping("/add")
    public String saveItemV2(@ModelAttribute("item") Item item) {
        Item save = itemRepository.save(item);
//        model.addAttribute("item", save); 자동 추가. 생략 가능.
        return "basic/item";
    }

    /**
     * 상품 저장 V3
     */
    @PostMapping("/add")
    public String saveItemV3(Item item, RedirectAttributes redirectAttributes) {
        Item save = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", save.getId());
        redirectAttributes.addAttribute("status", true);
        //        model.addAttribute("item", save); 자동 추가. 생략 가능.
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 상품 수정 폼
     */
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    /**
     * 상품 수정
     */
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}"; // 리다이렉
    }

    @GetMapping
    public String items(Model model) {
        List<Item> all = itemRepository.findAll();
        model.addAttribute("items", all);

        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String detailItem(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @PostConstruct
    public void init(){
        Item itemA = new Item("itemA", 1000, 100);
        Item itemB = new Item("itemB", 1000, 100);
        itemRepository.save(itemA);
        itemRepository.save(itemB);
    }
}
