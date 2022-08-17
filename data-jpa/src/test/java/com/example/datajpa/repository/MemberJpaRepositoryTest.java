package com.example.datajpa.repository;

import com.example.datajpa.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class MemberJpaRepositoryTest {
    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    void testMember() {
        Member member = new Member("memberA");
        memberJpaRepository.save(member);
        Member findMember = memberJpaRepository.find(member.getId());

        assertEquals(member.getId(), findMember.getId());
        assertEquals(member, findMember);
    }

    @Test
    void findByUsernameAndAgeGreaterThan() {
        Member ming1 = new Member("ming", 17);
        Member ming2 = new Member("ming", 24);
        memberJpaRepository.save(ming1);
        memberJpaRepository.save(ming2);

        List<Member> ming = memberJpaRepository.findByUsernameAndAgeGreaterThan("ming", 20);

        assertEquals(ming.get(0).getUsername(), "ming");
        assertEquals(ming.get(0).getAge(), 24);

    }
}