package com.alex.homework4example.mapper.impl;

import com.alex.homework4example.dto.CardDTO;
import com.alex.homework4example.entity.Card;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.dao.jdbc.impl.JdbcCustomerDao;
import com.alex.homework4example.dao.jdbc.impl.JdbcAccountDao;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CardMapper implements Mapper<Card, CardDTO> {

    private final JdbcCustomerDao jdbcCustomerDao;
    private final JdbcAccountDao jdbcAccountDao;

    public CardMapper(JdbcCustomerDao jdbcCustomerDao, JdbcAccountDao jdbcAccountDao) {
        this.jdbcCustomerDao = jdbcCustomerDao;
        this.jdbcAccountDao = jdbcAccountDao;
    }

    @Override
    public CardDTO toDto(Card card) {
        return CardDTO.builder()
                .id(card.getId())
                .accountId(card.getAccount().getId())
                .customerId(card.getCustomer().getId())
                .cardNumber(card.getCardNumber())
                .cardType(card.getCardType())
                .expirationDate(card.getExpirationDate())
                .cvv(card.getCvv())
                .createdAt(card.getCreatedAt())
                .build();
    }

    @Override
    public Card toEntity(CardDTO cardDTO) {
        Customer customer = jdbcCustomerDao.findById(cardDTO.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + cardDTO.getCustomerId() + " not found"));

        Account account = jdbcAccountDao.findById(cardDTO.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + cardDTO.getAccountId() + " not found"));

        return Card.builder()
                .id(cardDTO.getId())
                .account(account)
                .customer(customer)
                .cardNumber(cardDTO.getCardNumber())
                .cardType(cardDTO.getCardType())
                .expirationDate(cardDTO.getExpirationDate())
                .cvv(cardDTO.getCvv())
                .createdAt(cardDTO.getCreatedAt())
                .build();
    }
}
