package com.alex.homework4example.repository;

import com.alex.homework4example.entity.Account;
import com.alex.homework4example.entity.Account_;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepository extends AbstractRepository<Account> {

    public AccountRepository() {
        super(Account.class);
    }

    // Fetch using Criteria API
    public List<Account> findAllWithCustomerFetch() {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);
        root.fetch(Account_.CUSTOMER); // using fetch
        return entityManager.createQuery(query).getResultList();
    }

    // Fetch using JPQL
    public List<Account> findAllWithCustomerFetchJPQL() {
        String jpql = "SELECT a FROM Account a JOIN FETCH a.customer";
        TypedQuery<Account> query = entityManager.createQuery(jpql, Account.class);
        return query.getResultList();
    }

    // Fetch using EntityGraph
    public List<Account> findAllWithCustomerEntityGraph() {
        EntityGraph<Account> entityGraph = entityManager.createEntityGraph(Account.class);
        entityGraph.addAttributeNodes(Account_.CUSTOMER); //fetch

        TypedQuery<Account> query = entityManager.createQuery("SELECT a FROM Account a", Account.class);
        query.setHint("javax.persistence.loadgraph", entityGraph);
        return query.getResultList();
    }
}
