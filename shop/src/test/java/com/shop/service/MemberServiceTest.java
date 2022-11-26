package com.shop.service;


import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("멤버 생성 테스트")
    void saveMember() {
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);
        Assertions.assertThat(savedMember.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    void saveDuplicateMemberTest() {
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);
        assertThrows(IllegalArgumentException.class, () -> {
        memberService.saveMember(member2);
        });
    }

    private Member createMember() {
        MemberFormDto form = new MemberFormDto();
        form.setEmail("test@gmail.com");
        form.setAddress("서울시 마포구 합정동");
        form.setName("tester");
        form.setPassword("1234567");
        return Member.createMember(form, passwordEncoder);
    }
}