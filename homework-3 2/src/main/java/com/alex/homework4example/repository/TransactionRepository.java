package com.alex.homework4example.repository;

import com.alex.homework4example.entity.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository extends AbstractRepository<Transaction> {

    public TransactionRepository() {
        super(Transaction.class);
    }

}
