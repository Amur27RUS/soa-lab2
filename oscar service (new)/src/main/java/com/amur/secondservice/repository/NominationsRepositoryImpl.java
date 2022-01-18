package com.amur.secondservice.repository;

import com.amur.secondservice.entity.Nominations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import javax.persistence.criteria.Root;
import java.util.List;

public class NominationsRepositoryImpl {
    @PersistenceContext
    private Session session;
    @PersistenceContext
    private EntityManager em;

    public List<Nominations> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Nominations> criteriaQuery = criteriaBuilder.createQuery(Nominations.class);
        Root<Nominations> from = criteriaQuery.from(Nominations.class);
        CriteriaQuery<Nominations> select = criteriaQuery.select(from);
        TypedQuery<Nominations> typedQuery = em.createQuery(select);

        return typedQuery.getResultList();
    }

    public void create(Nominations nominations) {
        em.getTransaction().begin();
        em.persist(nominations);
        em.getTransaction().commit();
        em.clear();
    }

    public void update(Nominations nominations) {
        em.getTransaction().begin();
        em.merge(nominations);
        em.getTransaction().commit();
        em.clear();
    }
}
