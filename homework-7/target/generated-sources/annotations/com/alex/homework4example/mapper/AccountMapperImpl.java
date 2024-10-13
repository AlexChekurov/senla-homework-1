package com.alex.homework4example.mapper;

import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.dto.CustomerDTO;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.entity.Customer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-26T17:54:03+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account toEntity(AccountDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Account account = new Account();

        account.setId( dto.getId() );
        account.setAccountNumber( dto.getAccountNumber() );
        account.setAccountType( dto.getAccountType() );
        account.setBalance( dto.getBalance() );
        account.setCurrency( dto.getCurrency() );
        account.setIban( dto.getIban() );
        account.setCreatedAt( dto.getCreatedAt() );
        account.setCustomer( customerDTOToCustomer( dto.getCustomer() ) );

        return account;
    }

    @Override
    public AccountDTO toDto(Account entity) {
        if ( entity == null ) {
            return null;
        }

        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setId( entity.getId() );
        accountDTO.setAccountNumber( entity.getAccountNumber() );
        accountDTO.setAccountType( entity.getAccountType() );
        accountDTO.setBalance( entity.getBalance() );
        accountDTO.setCurrency( entity.getCurrency() );
        accountDTO.setIban( entity.getIban() );
        accountDTO.setCreatedAt( entity.getCreatedAt() );
        accountDTO.setCustomer( customerToCustomerDTO( entity.getCustomer() ) );

        return accountDTO;
    }

    protected Customer customerDTOToCustomer(CustomerDTO customerDTO) {
        if ( customerDTO == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.id( customerDTO.getId() );
        customer.firstName( customerDTO.getFirstName() );
        customer.lastName( customerDTO.getLastName() );
        customer.email( customerDTO.getEmail() );
        customer.phone( customerDTO.getPhone() );

        return customer.build();
    }

    protected CustomerDTO customerToCustomerDTO(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setId( customer.getId() );
        customerDTO.setFirstName( customer.getFirstName() );
        customerDTO.setLastName( customer.getLastName() );
        customerDTO.setEmail( customer.getEmail() );
        customerDTO.setPhone( customer.getPhone() );

        return customerDTO;
    }
}
