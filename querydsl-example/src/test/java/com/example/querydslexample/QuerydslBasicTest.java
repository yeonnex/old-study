package com.example.querydslexample;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {
    @Autowired
    EntityManager em;

    @BeforeEach
    void beforeEach(){
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
    }

    @Test
    void startJPQL(){
        // member1 을 찾아라.
        String query = "select m from Member m where m.memberName = :memberName";
        Member findMember = em.createQuery(query, Member.class).setParameter("memberName", "member1").getSingleResult();

        assertThat(findMember.getMemberName()).isEqualTo("member1");
    }

    @Test
    void startQueryDsl() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember m = new QMember("m");
        Member findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.memberName.eq("member1"))
                .fetchOne();

        assertThat(findMember.getMemberName()).isEqualTo("member1");
    }
}
