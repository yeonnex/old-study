package com.example.datajpa.repository;

import com.example.datajpa.dto.MemberDto;
import com.example.datajpa.entity.Member;
import com.example.datajpa.entity.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.temporal.Temporal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

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

    @Test
    void fineUsernameList() {
        Member ming = new Member("ming", 24);
        memberRepository.save(ming);
        Member mang = new Member("mang", 28);
        memberRepository.save(mang);

        List<String> usernameList = memberRepository.findUsernameList();

        usernameList.forEach(System.out::println);

    }

    @Test
    void findDto() {
        Team apple = new Team("Apple");
        Team google = new Team("Google");
        teamRepository.save(apple);
        teamRepository.save(google);

        Member ming = new Member("ming", 24, apple);
        memberRepository.save(ming);
        Member mang = new Member("mang", 28, google);
        memberRepository.save(mang);

        List<MemberDto> memberDtoList = memberRepository.findMemberDto();
        assertThat(memberDtoList.get(0)).isInstanceOf(MemberDto.class);
        memberDtoList.forEach(System.out::println);
    }

}