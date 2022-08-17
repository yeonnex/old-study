package com.example.datajpa.repository;

import com.example.datajpa.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    void memberTest(){
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertThat(member).isEqualTo(savedMember);
        assertEquals(savedMember.getId(), findMember.getId());

    }

    @Test
    void greaterThan() {
        Member ming1 = new Member("ming", 17);
        Member ming2 = new Member("ming", 24);
        memberRepository.save(ming1);
        memberRepository.save(ming2);

        List<Member> ming = memberRepository.findByUsernameAndAgeGreaterThan("ming", 20);

        assertEquals(ming.get(0).getUsername(), "ming");
        assertEquals(ming.get(0).getAge(), 24);
    }

    @Test
    void findUserAnnoQuery() {
        Member member = new Member("ming", 24);
        memberRepository.save(member);
        List<Member> ming = memberRepository.findUser("ming", 24);

        assertEquals(ming.get(0).getAge(), 24);
    }

}