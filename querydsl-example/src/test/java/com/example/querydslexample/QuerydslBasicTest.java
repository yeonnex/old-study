package com.example.querydslexample;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static com.example.querydslexample.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.shouldHaveThrown;
import static org.assertj.core.api.InstanceOfAssertFactories.map;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {
    @Autowired
    EntityManager em;

    private JPAQueryFactory queryFactory;

    @BeforeEach
    void beforeEach(){
        queryFactory = new JPAQueryFactory(em);
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
        Member findMember = queryFactory
                .select(member) // static import
                .from(member)
                .where(member.memberName.eq("member1"))
                .fetchOne();

        assertThat(findMember.getMemberName()).isEqualTo("member1");
    }

    @Test
    public void searchAndParm() {

        Member seoyeon = queryFactory
                .select(member)
                .where(
                        member.memberName.eq("seoyeon"),
                        member.age.eq(24)
                ).fetchOne();

    }

    @Test
    public void resultFetch() {
//        // Member 리스트로 조회
//        List<Member> fetch = queryFactory
//                .selectFrom(member)
//                .fetch();
//
//        // Member 단건 조회
//        Member one = queryFactory
//                .selectFrom(member)
//                .fetchOne();
//
//        queryFactory
//                .selectFrom(member)
//                .fetchFirst(); // limit(1).fetchOne(); 과 똑같음

        // total count 가져오기 - 쿼리가 두번 실행됨
        QueryResults<Member> results = queryFactory
                .selectFrom(member)
                .fetchResults();

        results.getTotal();
        List<Member> members = results.getResults();

        // count 만 가져오기
        queryFactory
                .selectFrom(member)
                .fetchCount();
    }

    /**
     * 회원 정렬 순서
     * 1. 회원 나이 내림차순 (desc)
     * 2. 회원 이름 올림차순 (asc)
     * 단 2에서 회원 이름이 없으면 마지막에 출력 (nulls last)
     */
    @Test
    void sort() {
        // 회원 추가
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));
        em.flush();
        em.clear();

        // 회원 정렬
        List<Member> members = queryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.memberName.asc().nullsLast())
                .fetch();

        members.forEach(member -> {
            System.out.println(member.getMemberName());
        });

        assertThat(members.get(0).getMemberName()).isEqualTo("member5");
        assertThat(members.get(1).getMemberName()).isEqualTo("member6");
        assertThat(members.get(2).getMemberName()).isNull();


    }
}
