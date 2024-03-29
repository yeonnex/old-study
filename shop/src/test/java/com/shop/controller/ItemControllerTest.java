package com.shop.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class ItemControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("상품 등록 페이지 어드민 권한 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void itemFormTest() throws Exception {
        mockMvc.perform(get("/admin/item/new"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 등록 페이지 일반 회원 권한 접근 테스트")
    @WithMockUser(username = "user", roles = "USER")
    void itemFormNotAdminTest() throws Exception {
        mockMvc.perform(get("/admin/item/new"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}