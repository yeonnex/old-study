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

    @Test
    public void paging() {
        // given
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 10));
        memberJpaRepository.save(new Member("member3", 10));
        memberJpaRepository.save(new Member("member4", 10));
        memberJpaRepository.save(new Member("member5", 10));

        int age = 10;
        int offset = 0;
        int limit = 3;

        // when
        List<Member> byPage = memberJpaRepository.findByPage(age, offset, limit);
        long total = memberJpaRepository.totalCountByAge(age);

        // then
        assertEquals(byPage.size(), limit);
        assertEquals(total, 5);
    }
}