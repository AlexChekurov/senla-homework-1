package com.alex.homework4example.mapper;

import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.dto.CardDTO;
import com.alex.homework4example.dto.CustomerDTO;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.entity.Card;
import com.alex.homework4example.entity.Customer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-26T17:54:03+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class CardMapperImpl implements CardMapper {

    @Override
    public Card toEntity(CardDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Card card = new Card();

        card.setId( dto.getId() );
        card.setCardNumber( dto.getCardNumber() );
        card.setCardType( dto.getCardType() );
        card.setExpirationDate( dto.getExpirationDate() );
        card.setCvv( dto.getCvv() );
        if ( dto.getCreatedAt() != null ) {
            card.setCreatedAt( dto.getCreatedAt().toLocalDate() );
        }
        card.setAccount( accountDTOToAccount( dto.getAccount() ) );

        return card;
    }

    @Override
    public CardDTO toDto(Card entity) {
        if ( entity == null ) {
            return null;
        }

        CardDTO cardDTO = new CardDTO();

        cardDTO.setId( entity.getId() );
        cardDTO.setCardNumber( entity.getCardNumber() );
        cardDTO.setCardType( entity.getCardType() );
        cardDTO.setExpirationDate( entity.getExpirationDate() );
        cardDTO.setCvv( entity.getCvv() );
        if ( entity.getCreatedAt() != null ) {
            cardDTO.setCreatedAt( entity.getCreatedAt().atStartOfDay() );
        }
        cardDTO.setAccount( accountToAccountDTO( entity.getAccount() ) );

        return cardDTO;
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
        account.setCustomer( customerDTOToCustomer( accountDTO.getCustomer() ) );

        return account;
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
        accountDTO.setCustomer( customerToCustomerDTO( account.getCustomer() ) );

        return accountDTO;
    }
}
