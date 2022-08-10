package com.example.jpashop.service;


import com.example.jpashop.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원 가입 - 정상")
    void 회원가입_정상() {
        Member member = Member.builder().name("seoyeon").build();
        Long memberId = memberService.join(member);

        Member savedMember = memberService.findOne(memberId);
        assertEquals(member, savedMember);
    }

    @Test
    @DisplayName("회원가입 - 이름 중복으로 인한 예외 발생")
    void 회원가입_중복() {
        Member member_1 = Member.builder().name("seoyeon").build();
        Long memberId_1 = memberService.join(member_1);

        Member member_2 = Member.builder().name("seoyeon").build();

        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member_2);
        });
    }


}