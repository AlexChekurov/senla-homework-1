package com.alex.homework4example.service.impl;

import com.alex.homework4example.dto.TransactionDTO;
import com.alex.homework4example.entity.Transaction;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.mapper.CommonMapper;
import com.alex.homework4example.repository.AbstractRepository;
import com.alex.homework4example.service.TransactionService;
import org.springframework.stereotype.Service;


@Service
public class TransactionServiceImpl extends AbstractCrudService<Transaction, TransactionDTO>
        implements TransactionService {

    public TransactionServiceImpl(
            AbstractRepository<Transaction> repository,
            CommonMapper<Transaction, TransactionDTO> mapper) {
        super(repository, mapper);
    }

    @Override
    public TransactionDTO update(Long id, TransactionDTO transactionDetails) {
        return findById(id)
                .map(transaction -> {
                    transaction.setAmount(transactionDetails.getAmount());
                    transaction.setTransactionDate(transactionDetails.getTransactionDate());
                    transaction.setCurrency(transactionDetails.getCurrency());
                    return updateEntityToDto(transaction);
                })
                .orElseThrow(() -> new EntityNotFoundException("Can't update transaction with id: " + id));
    }

}
