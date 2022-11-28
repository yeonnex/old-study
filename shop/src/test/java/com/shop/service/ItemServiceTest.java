package com.shop.service;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ItemServiceTest {
    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    List<MultipartFile> createMultipartFiles() throws Exception {
        List<MultipartFile> multipartFileList = new ArrayList<>();
        for (int i = 0; i < 5 ; i++) {
            String path = "/User/yeonnex/Desktop";
            String imageName = "image_" + i + ".jpg";
            MockMultipartFile multipartFile = new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1, 2, 3, 4});
            multipartFileList.add(multipartFile);
        }
        return multipartFileList;
    }

    @Test
    @DisplayName("상품등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveItem() throws Exception {
        ItemFormDto form = new ItemFormDto();
        form.setItemNm("테스트 상품");
        form.setItemSellStatus(ItemSellStatus.SELL);
        form.setItemDetail("테스트 상품입니다");
        form.setPrice(1000);
        form.setStockNumber(100);
        // 이미지 파일 생성
        List<MultipartFile> multipartFiles = createMultipartFiles();
        // 아이템과 아이템이미지 저장
        Long itemId = itemService.saveItem(form, multipartFiles);

        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderById(itemId);

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

        // 입력한 상품데이터와 실제로 저장된 상품데이터가 같은지 확인
        assertThat(form.getItemNm()).isEqualTo(item.getItemNm());
        assertThat(form.getStockNumber()).isEqualTo(item.getStockNumber());

        // 상품 이미지들 저장 확인
        assertThat(multipartFiles.get(0).getOriginalFilename()).isEqualTo(itemImgList.get(0).getOriImgName());
    }
}