package com.shop.service;

import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemImgDto;
import com.shop.dto.ItemSearchDto;
import com.shop.dto.MainItemDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;
    private final ModelMapper modelMapper;

    /**
     * 상품 저장
     * @param itemFormDto
     * @param itemImgFileList
     * @return
     * @throws Exception
     */

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        // 상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        // 이미지 등록
        for (int i = 0; i < itemImgFileList.size() ; i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if (i == 0) {
                itemImg.setIsRepImg("Y");
            } else {
                itemImg.setIsRepImg("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

        return item.getId();
    }

    /**
     * itemId 로 상품상세 조회
     * @param itemId
     * @return
     */
    @Transactional(readOnly = true)
    public  ItemFormDto getItemDetail(Long itemId) {
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderById(itemId);
        List<ItemImgDto> itemImgDtoList = itemImgList
                .stream()
                .map(itemImg -> modelMapper.map(itemImg, ItemImgDto.class)).toList();

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);

        // 상품DTO에 상품이미지 세팅
        itemFormDto.setItemImgDtoList(itemImgDtoList);

        return itemFormDto;
    }

    public Long updateItem(ItemFormDto form, List<MultipartFile> itemImgFileList) throws Exception {
        // 상품 수정
        Item item = itemRepository.findById(form.getId()).orElseThrow(EntityNotFoundException::new);
        item.updateItem(form);

        List<Long> itemImgIds = form.getItemImgIds();
        // 이미지 등록
        for (int i = 0 ; i < itemImgIds.size() ; i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(0));
        }

        return item.getId();
    }

    /**
     * 아이템 조회 조건과 페이지 정보를 파라미터로 받아 상품 데이터를 조회
     */
    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    /**
     * 메인화면용 상품조회
     */
    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }
}
