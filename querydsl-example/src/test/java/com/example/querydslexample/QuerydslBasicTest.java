package com.example.querydslexample;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static com.example.querydslexample.QMember.member;
import static com.example.querydslexample.QTeam.team;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {
    @Autowired
    EntityManager em;

    private JPAQueryFactory queryFactory;

    @BeforeEach
    void beforeEach() {
        queryFactory = new JPAQueryFactory(em);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamB);
        Member member3 = new Member("member3", 10);
        Member member4 = new Member("member4", 20);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);


        // 초기화
        em.flush();
        em.clear(); // 영속성 컨텍스트 초기화
    }

    @Test
    void startJPQL() {
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

    @Test
    void paging1() {
        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.memberName.desc())
                .offset(1)
                .limit(2)
                .fetch();

        assertThat(result.size()).isEqualTo(2);
    }

    /**
     * 팀의 이름과 각 팀의 평균 연령을 구해라.
     */
    @Test
    void groupBy() {
        List<Tuple> result = queryFactory
                .select(team.teamName, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.teamName)
                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        assertThat(teamA.get(member.age.avg())).isEqualTo(10);
        assertThat(teamA.get(team.teamName)).isEqualTo("teamA");
        assertThat(teamB.get(member.age.avg())).isEqualTo(20);
        assertThat(teamB.get(team.teamName)).isEqualTo("teamB");
    }

    @Test
    void theta_join() {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));

        em.flush();
        em.clear();

        List<Member> result = queryFactory
                .selectFrom(member)
                .from(member, team)
                .where(member.memberName.eq(team.teamName))
                .fetch();

        assertThat(result)
                .extracting("memberName")
                .containsExactly("teamA", "teamB");
    }

    /**
     * 예) 회원과 팀을 조인하면서, 팀 이름이 teamA 인 팀만 조인, 회원은 모두 조회
     * JPQL: select m, t from Member m left join m.team t on t.name = 'teamA'
     */
    @Test
    void join_on_filtering() {
        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team)
                .on(team.teamName.eq("teamA"))
                .fetch();

        result.forEach(System.out::println);

    }
    @PersistenceUnit
    EntityManagerFactory emf;
    @Test
    void fetchJoinNo() {
        Member result = queryFactory
                .selectFrom(member)
                .where(member.memberName.eq("member1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(result.getTeam());
        assertThat(loaded).as("페치 조인 미적용").isFalse();

    }

    @Test
    void fetchJoinYes() {
        Member result = queryFactory
                .selectFrom(member)
                .join(member.team, team).fetchJoin() // 페치 조인
                .where(member.memberName.eq("member1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(result.getTeam());
        assertThat(loaded).isTrue();
    }

    @


}
