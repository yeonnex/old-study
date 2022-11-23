package com.example.querydslexample;

import com.example.querydslexample.dto.MemberDto;
import com.example.querydslexample.dto.QMemberDto;
import com.example.querydslexample.dto.UserDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
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
import static com.querydsl.core.types.Projections.bean;
import static com.querydsl.core.types.Projections.fields;
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
        Member member4 = new Member("member4", 100);

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

    /**
     * 나이가 가장 많은 회원 조회
     */
    @Test
    void subQuery() {
        QMember memberSub = new QMember("memberSub");
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(
                        member.age.eq(
                                JPAExpressions
                                        .select(memberSub.age.max())
                                        .from(memberSub)
                        )
                )
                .fetch();

        assertThat(result).extracting("age").containsExactly(100);

    }

    /**
     * 나이가 평균 이상인 회원 조회
     */
    @Test
    void subQueryGoe() {
        QMember memberSub = new QMember("memberSub");
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(
                        member.age.goe(
                                JPAExpressions
                                        .select(memberSub.age.avg())
                                        .from(memberSub)
                        ))
                .fetch();

        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void concat() {
        List<String> result = queryFactory
                .select(member.memberName.concat("🤓").concat(member.age.stringValue()))
                .from(member)
                .fetch();
        for (String s : result) {
            System.out.println(s);
        }
    }

    /**
     * 순수 JPA 에서 DTO 조회
     * - 순수 JPA 에서 DTO 를 조회할 때는 new 명령어를 사용해야함 <br><br/>
     * - DTO 의 패키지 이름을 다 적어줘야해서 지저분함 <br><br/>
     * - 생성자 방식만 지원함 <br><br/>
     */
    @Test
    void findDtoByJPQL() {
        List<MemberDto> resultList = em.createQuery("select new com.example.querydslexample.dto.MemberDto(m.memberName, m.age) from Member m", MemberDto.class)
                .getResultList();

        assertThat(resultList.get(0)).isInstanceOf(MemberDto.class);
    }

    /**
     * QueryDSL 을 사용하여 DTO 조회하기
     * - 프로퍼티 접근
     * - 필드 직접 접근
     */
    @Test
    void findDtoByQueryDslSetter() {
        List<MemberDto> result = queryFactory
                .select(bean(MemberDto.class, member.memberName, member.age)) // setter 를 통해 값 삽입
                .from(member)
                .fetch();

        assertThat(result.get(0)).isInstanceOf(MemberDto.class);
    }

    @Test
    void findDtoByQueryDslField() {
        List<MemberDto> result = queryFactory
                .select(fields(MemberDto.class, member.memberName, member.age)) // 필드에 직접 값 삽입
                .from(member)
                .fetch();

        assertThat(result.get(0)).isInstanceOf(MemberDto.class);
    }

    @Test
    void findUserDtoByQueryDsl() {
        List<UserDto> result = queryFactory
                .select(fields(UserDto.class, member.memberName.as("name"), member.age))
                .from(member)
                .fetch();

        assertThat(result.get(0)).isInstanceOf(UserDto.class);
        for (UserDto userDto : result) {
            System.out.println(userDto);
        }
    }

    @Test
    void findDtoByQueryProjection() {
        List<MemberDto> result = queryFactory
                .select(new QMemberDto(member.memberName, member.age))
                .from(member)
                .fetch();

        assertThat(result.get(0)).isInstanceOf(MemberDto.class);
    }

    @Test
    void dynamicQuery_BooleanBuilder() {
        String usernameParam = "member1";
        Integer ageParam = null;

        List<Member> result = searchMember1(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void dynamicQuery_WhereParm() {
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMember2(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember2(String usernameCond, Integer ageCond) {
        return queryFactory
                .selectFrom(member)
                .where(usernameEq(usernameCond), ageEq(ageCond))
                .fetch();
    }

    private Predicate ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }

    private Predicate usernameEq(String usernameCond) {
        return usernameCond != null ? member.memberName.eq(usernameCond) : null;
    }

    private List<Member> searchMember1(String usernameCond, Integer ageCond) {

        BooleanBuilder builder = new BooleanBuilder();
        if (usernameCond != null) {
            builder.and(member.memberName.eq(usernameCond));
        }
        if (ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }

        return queryFactory
                .selectFrom(member)
                .where(builder)
                .fetch();

    }


}
