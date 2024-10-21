package com.alex.homework4example.repository;

import com.alex.homework4example.entity.User;
import org.springframework.stereotype.Repository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.Optional;

@Repository
public class UserRepository extends AbstractRepository<User> {

    public UserRepository() {
        super(User.class);
    }

    public Optional<User> findByUsername(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Predicate condition = cb.equal(root.get("username"), username);
        query.select(root).where(condition);

        try {
            User user = entityManager.createQuery(query).getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
