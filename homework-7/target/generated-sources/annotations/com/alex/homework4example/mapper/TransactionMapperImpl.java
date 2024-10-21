package com.alex.homework4example.mapper;

import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.dto.TransactionDTO;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.entity.Transaction;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-21T21:24:34+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction toEntity(TransactionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setId( dto.getId() );
        transaction.setAmount( dto.getAmount() );
        transaction.setTransactionDate( dto.getTransactionDate() );
        transaction.setCurrency( dto.getCurrency() );
        transaction.setFromAccount( accountDTOToAccount( dto.getFromAccount() ) );
        transaction.setToAccount( accountDTOToAccount( dto.getToAccount() ) );

        return transaction;
    }

    @Override
    public TransactionDTO toDto(Transaction entity) {
        if ( entity == null ) {
            return null;
        }

        TransactionDTO transactionDTO = new TransactionDTO();

        transactionDTO.setId( entity.getId() );
        transactionDTO.setAmount( entity.getAmount() );
        transactionDTO.setTransactionDate( entity.getTransactionDate() );
        transactionDTO.setFromAccount( accountToAccountDTO( entity.getFromAccount() ) );
        transactionDTO.setToAccount( accountToAccountDTO( entity.getToAccount() ) );
        transactionDTO.setCurrency( entity.getCurrency() );

        return transactionDTO;
    }

    protected Account accountDTOToAccount(AccountDTO accountDTO) {
        if ( accountDTO == null ) {
            return null;
        }

        Account account = new Account();

        account.setId( accountDTO.getId() );
        account.setAccountNumber( accountDTO.getAccountNumber() );
        account.setAccountType( accountDTO.getAccountType() );
        account.setBalance( accountDTO.getBalance() );
        account.setCurrency( accountDTO.getCurrency() );
        account.setIban( accountDTO.getIban() );
        account.setCreatedAt( accountDTO.getCreatedAt() );

        return account;
    }

    protected AccountDTO accountToAccountDTO(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setId( account.getId() );
        accountDTO.setAccountNumber( account.getAccountNumber() );
        accountDTO.setAccountType( account.getAccountType() );
        accountDTO.setBalance( account.getBalance() );
        accountDTO.setCurrency( account.getCurrency() );
        accountDTO.setIban( account.getIban() );
        accountDTO.setCreatedAt( account.getCreatedAt() );

        return accountDTO;
    }
}
