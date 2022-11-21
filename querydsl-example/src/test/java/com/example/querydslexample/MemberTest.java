package com.example.querydslexample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberTest {
    @Autowired
    EntityManager em;

    @Test
    public void testEntity() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamB);

        em.persist(member1);
        em.persist(member2);

        // 초기화
        em.flush();
        em.clear(); // 영속성 컨텍스트 초기화

        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        assertThat(members.size()).isEqualTo(2);

        List<Team> teams = em.createQuery("select m from Team m", Team.class).getResultList();
        assertThat(teams.size()).isEqualTo(2);
    }
}