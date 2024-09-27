package com.alex.homework4example.repository;

import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.entity.Customer_;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository extends AbstractRepository<Customer> {

    public CustomerRepository() {
        super(Customer.class);
    }

    // Fetch using Criteria API
    public List<Customer> findAllWithAccountsFetch() {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<Customer> query = cb.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);
        root.fetch(Customer_.ACCOUNTS); // fetch
        return entityManager.createQuery(query).getResultList();
    }

    // Fetch using JPQL
    public List<Customer> findAllWithAccountsFetchJPQL() {
        String jpql = "SELECT c FROM Customer c JOIN FETCH c.accounts";
        TypedQuery<Customer> query = entityManager.createQuery(jpql, Customer.class);
        return query.getResultList();
    }

    // Fetch using EntityGraph
    public List<Customer> findAllWithAccountsEntityGraph() {
        EntityGraph entityGraph = entityManager.createEntityGraph(Customer.class);
        entityGraph.addAttributeNodes(Customer_.ACCOUNTS); // fetch

        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
        query.setHint("javax.persistence.loadgraph", entityGraph);
        return query.getResultList();
    }
}
