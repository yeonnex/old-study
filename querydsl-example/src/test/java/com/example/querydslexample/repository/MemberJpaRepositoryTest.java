package com.example.querydslexample.repository;

import com.example.querydslexample.Member;
import com.example.querydslexample.Team;
import com.example.querydslexample.dto.MemberSearchCondition;
import com.example.querydslexample.dto.MemberTeamDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository repository;

    @Autowired
    EntityManager em;
    @Test
    void basicTest() {
        Member member = new Member("member1");
        repository.save(member);
        Member savedMember = repository.findById(member.getId());
        assertThat(member).isEqualTo(savedMember);

        List<Member> all = repository.findAll();
        assertThat(all).containsExactly(member);

        Member byUserName = repository.findByUserName("member1");
        assertThat(member).isEqualTo(byUserName);
    }

    @Test
    void queryDslTest() {
        Member member = new Member("member1");
        repository.save(member);

        List<Member> all = repository.findAll_QueryDsl();
        assertThat(all).containsExactly(member);

        Member byUserName = repository.findByUsername_QueryDsl("member1");
        assertThat(member).isEqualTo(byUserName);
    }

    @Test
    void searchTest_builder() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamB);
        Member member3 = new Member("member3", 30);
        Member member4 = new Member("member4", 40);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(12);
        condition.setAgeLoe(32);
        condition.setTeamName("teamB");

        List<MemberTeamDto> result = repository.searchByBuilder(condition);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getMemberName()).isEqualTo("member2");
    }


    @Test
    void search_where() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamB);
        Member member3 = new Member("member3", 30);
        Member member4 = new Member("member4", 40);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(31);
        condition.setAgeLoe(45);

        List<MemberTeamDto> result = repository.searchByWhere(condition);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).extracting("memberName").isEqualTo("member4");

    }

}