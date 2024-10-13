package com.alex.homework4example.mapper;

import com.alex.homework4example.dto.TransactionDTO;
import com.alex.homework4example.entity.Transaction;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-26T17:54:03+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
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
        transactionDTO.setCurrency( entity.getCurrency() );

        return transactionDTO;
    }
}
