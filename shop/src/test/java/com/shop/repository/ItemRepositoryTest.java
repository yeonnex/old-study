package com.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    void createItem() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("상세설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        itemRepository.save(item);
        Optional<Item> savedItem = itemRepository.findById(item.getId());
        assertThat(item.getId()).isEqualTo(savedItem.get().getId());
    }

    @Test
    @DisplayName("상품이름으로 상품 조회 테스트")
    void findByItemTest() {
        createItemList();
        String itemName = "테스트 상품 1";
        Item item = itemRepository.findByItemNm(itemName);

        assertThat(item.getItemNm()).isEqualTo(itemName);
    }

    @Test
    @DisplayName("상품상세설명에 검색어가 포함된 상품 가격이 높은 순으로 조회 테스트")
    void findByItemDetail() {
        createItemList();
        List<Item> items = itemRepository.findByItemDetail("7"); // 상품상세에 "7" 이 있는 상품 조회

        assertThat(items.get(0).getItemNm()).isEqualTo("테스트 상품 7");

    }

    @Test
    @DisplayName("상품 QueryDsl 조회테스트")
    void findByItemDetail_querydsl() {
        this.createItemList();

        BooleanBuilder builder = new BooleanBuilder();
        QItem item = QItem.item;
        // 아래 두 조건에 부합하는 상품 찾기
        String itemDetail = "상세설명 7";
        int price = 10009;
        ItemSellStatus status = ItemSellStatus.SELL;

        // 빌더로 조건 생성
        builder.and(item.itemDetail.like("%" + itemDetail + "%"));
        builder.and(item.price.loe(price));
        builder.and(item.itemSellStatus.eq(status));

        PageRequest pageable = PageRequest.of(0, 5);
        Page<Item> result = itemRepository.findAll(builder, pageable);

        Item resultItem = result.getContent().get(0);

        assertThat(resultItem.getItemDetail()).contains("상세설명 7");
        assertThat(result.getContent().size()).isEqualTo(1);
    }


    // ======= private method =======
    private void createItemList() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품 " + i);
            item.setPrice(10000 + i);
            item.setItemDetail("상세설명 " + i);

            ItemSellStatus status = i % 2 == 0 ? ItemSellStatus.SOLD_OUT : ItemSellStatus.SELL;
            item.setItemSellStatus(status);

            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            itemRepository.save(item);
        }
    }
}