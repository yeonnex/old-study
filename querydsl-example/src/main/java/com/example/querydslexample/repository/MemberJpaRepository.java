package com.example.querydslexample.repository;

import com.example.querydslexample.Member;
import com.example.querydslexample.QTeam;
import com.example.querydslexample.dto.MemberSearchCondition;
import com.example.querydslexample.dto.MemberTeamDto;
import com.example.querydslexample.dto.QMemberDto;
import com.example.querydslexample.dto.QMemberTeamDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.querydslexample.QMember.member;
import static com.example.querydslexample.QTeam.*;
import static org.springframework.util.StringUtils.*;

@Repository
public class MemberJpaRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MemberJpaRepository(EntityManager em, JPAQueryFactory queryFactory) {
        this.em = em;
        this.queryFactory = queryFactory;
    }

    public void save(Member member) {
        em.persist(member);
    }

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public Member findByUserName(String memberName) {
        return em.createQuery("select m from Member m where m.memberName = :memberName", Member.class)
                .setParameter("memberName", memberName)
                .getSingleResult();
    }

    // ==== QueryDsl =====
    public List<Member> findAll_QueryDsl() {
        return queryFactory.selectFrom(member)
                .fetch();
    }

    public Member findByUsername_QueryDsl(String username) {
        return queryFactory
                .selectFrom(member)
                .where(member.memberName.eq(username))
                .fetchOne();
    }

    public List<MemberTeamDto> searchByBuilder(MemberSearchCondition condition) {
        return searchMember(condition);
    }

    public List<MemberTeamDto> searchByWhere(MemberSearchCondition condition) {
        return queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                        member.memberName,
                        member.age,
                        team.id.as("teamId"),
                        team.teamName
                ))
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        memberNameEq(condition.getUsername()),
                            teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .fetch();
    }

    private Predicate ageLoe(Integer ageLoe) {
        return ageLoe != null ? member.age.loe(ageLoe) : null;
    }

    private Predicate ageGoe(Integer ageGoe) {
        return ageGoe != null ?  member.age.goe(ageGoe) : null;
    }

    private Predicate teamNameEq(String teamName) {
        return hasText(teamName) ? team.teamName.eq(teamName) : null;
    }

    private Predicate memberNameEq(String username) {
        return hasText(username) ? member.memberName.eq(username) : null;
    }

    // ===== private method =====
    private List<MemberTeamDto> searchMember(MemberSearchCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();
        String username = condition.getUsername();
        Integer ageLoe = condition.getAgeLoe();
        Integer ageGoe = condition.getAgeGoe();
        String teamName = condition.getTeamName();

        if (hasText(username)) {
            builder.and(member.memberName.eq(username));
        }
        if (hasText(teamName)) {
            builder.and(team.teamName.eq(teamName));
        }

        if (ageGoe != null) {
            builder.and(member.age.goe(ageGoe));
        }

        if (ageGoe != null) {
            builder.and(member.age.loe(ageLoe));
        }

        return queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                            member.memberName,
                        member.age,
                        team.id.as("teamId"),
                        team.teamName
                ))
                .from(member)
                .leftJoin(member.team, team)
                .where(builder)
                .fetch();

    }


}

