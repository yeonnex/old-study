package com.example.datajpa.repository;

import com.example.datajpa.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {
    @PersistenceContext
    private EntityManager em;

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

    public void delete(Member member) {
        em.remove(member); // delete 쿼리가 나갑니다.
    }

    public List<Member> findAll(){
        return em.createQuery(
                "select m from Member m" , Member.class
        ).getResultList();
    }

    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public long count() {
        return em.createQuery(
                "select count(m) from Member  m", Long.class
        ).getSingleResult();
    }

    public List<Member> findByUsernameAndAgeGreaterThan(String username, int age) {
        return em.createQuery(
                "select m from Member m" +
                        " where m.age > :age and m.username = :username", Member.class
        )       .setParameter("username", username)
                .setParameter("age", age)
                .getResultList();
    }

    public List<Member> findByPage(int age, int offset, int limit) {
        return em.createQuery(
                "select m from Member m where m.age = :age order by m.username desc", Member.class
        )
                .setParameter("age", age)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public long totalCountByAge(int age) {
        return em.createQuery(
                "select count(m) from Member m where m.age = :age", Long.class
        )
                .setParameter("age", age)
                .getSingleResult();
    }


}

