package com.alex.homework4example.mapper;

import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.entity.Account;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-21T21:08:48+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 20.0.2.1 (Amazon.com Inc.)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account toEntity(AccountDTO accountDTO) {
        if ( accountDTO == null ) {
            return null;
        }

        Account.AccountBuilder account = Account.builder();

        account.id( accountDTO.getId() );
        account.accountNumber( accountDTO.getAccountNumber() );
        account.accountType( accountDTO.getAccountType() );
        account.balance( accountDTO.getBalance() );
        account.currency( accountDTO.getCurrency() );
        account.iban( accountDTO.getIban() );
        account.createdAt( accountDTO.getCreatedAt() );

        return account.build();
    }

    @Override
    public AccountDTO toDto(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDTO.AccountDTOBuilder accountDTO = AccountDTO.builder();

        accountDTO.id( account.getId() );
        accountDTO.accountNumber( account.getAccountNumber() );
        accountDTO.accountType( account.getAccountType() );
        accountDTO.balance( account.getBalance() );
        accountDTO.currency( account.getCurrency() );
        accountDTO.iban( account.getIban() );
        accountDTO.createdAt( account.getCreatedAt() );

        return accountDTO.build();
    }
}
