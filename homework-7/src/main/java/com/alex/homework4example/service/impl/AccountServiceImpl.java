package com.alex.homework4example.service.impl;

import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.exception.InsufficientFundsException;
import com.alex.homework4example.mapper.CommonMapper;
import com.alex.homework4example.repository.AbstractRepository;
import com.alex.homework4example.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl extends AbstractCrudService<Account, AccountDTO> implements AccountService {


    public AccountServiceImpl(AbstractRepository<Account> repository, CommonMapper<Account, AccountDTO> mapper) {
        super(repository, mapper);
    }

    @Transactional
    public void transferMoney(Account fromAccount, Account toAccount, BigDecimal amount) {

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Not enough funds in the source account");
        }
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        repository.update(fromAccount);
        repository.update(toAccount);
    }
}
