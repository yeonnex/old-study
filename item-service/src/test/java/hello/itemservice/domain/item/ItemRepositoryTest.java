package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    @DisplayName("아이템을 저장한다")
    void save() {
        // given
        Item item = Item.builder().itemName("물풍선").price(500).quantity(200).build();
        // when
        Item save = itemRepository.save(item);
        // then
        assertThat(itemRepository.findById(save.getId())).isEqualTo(save);
    }

    @Test
    @DisplayName("아이템을 모두 조회한다")
    void findAll() {
        // given
        Item item1 = new Item("item1", 100, 50);
        Item item2 = new Item("item2", 200, 50);
        // when
        itemRepository.save(item1);
        itemRepository.save(item2);
        // then
        List<Item> result = itemRepository.findAll();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1, item2);
    }

    @Test
    @DisplayName("아이템을 업데이트한다")
    void update() {
        // given
        Item item1 = new Item("item1", 100, 50);
        
        Item savedItem = itemRepository.save(item1);
        Long itemId = savedItem.getId();
        
        // when
        Item updateParam = new Item("item2", 20000, 10000);
        itemRepository.update(itemId, updateParam);

        // then
        Item findItem = itemRepository.findById(itemId);
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }
}