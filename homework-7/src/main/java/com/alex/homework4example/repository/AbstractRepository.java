package com.alex.homework4example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
public abstract class AbstractRepository<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> entityType;

    @Transactional
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    public List<T> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityType);
        query.from(entityType);
        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    public List<T> findAll(int page, int size, Map<String, Object> params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityType);
        Root<T> root = query.from(entityType);
        if (CollectionUtils.isEmpty(params)) {
            query.select(root);
        } else {
            var predicates = params.entrySet().stream()
                    .map(entry -> cb.equal(root.get(entry.getKey()), entry.getValue()))
                    .toList();
            query.select(root).where(predicates.toArray(new Predicate[0]));
        }

        return entityManager.createQuery(query)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Transactional
    public Optional<T> findById(Long id) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityType);
        Root<T> root = query.from(entityType);
        Predicate condition = cb.equal(root.get("id"), id);
        query.select(root).where(condition);

        // Execute the query and retrieve results
        try {
            return Optional.ofNullable(entityManager.createQuery(query).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Transactional
    public int deleteAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<T> criteriaDelete = cb.createCriteriaDelete(entityType);
        criteriaDelete.from(entityType);

        int deletedCount = entityManager.createQuery(criteriaDelete).executeUpdate();
        if (deletedCount > 0) {
            entityManager.clear();
        }

        return deletedCount;
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
