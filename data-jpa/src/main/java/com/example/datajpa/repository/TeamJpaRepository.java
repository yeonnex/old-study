package com.example.datajpa.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TeamJpaRepository {
    @PersistenceContext
    EntityManager em;
}
