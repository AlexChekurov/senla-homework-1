package com.alex.homework4example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractDao<T> {

    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> entityType;

    @Transactional
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public List<T> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityType);
        query.from(entityType);
        return entityManager.createQuery(query).getResultList();
    }

    public Optional<T> findById(Long id) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityType);
        Root<T> root = query.from(entityType);
        Predicate condition = cb.equal(root.get("id"), id);
        query.select(root).where(condition);
        return Optional.ofNullable(entityManager.createQuery(query).getSingleResult());
    }

    @Transactional
    public int deleteAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<T> criteriaDelete = cb.createCriteriaDelete(entityType);
        return entityManager.createQuery(criteriaDelete).executeUpdate();
    }

    @Transactional
    public boolean deleteById(Long id) {
        T entity = findById(id).orElse(null);
        if (entity != null) {
            entityManager.remove(entity);
            return true;
        }
        return false;
    }

    protected CriteriaBuilder getCriteriaBuilder() {
        return entityManager.getCriteriaBuilder();
    }

}
