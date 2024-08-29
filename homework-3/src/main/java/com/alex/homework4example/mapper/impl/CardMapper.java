package com.alex.homework4example.mapper.impl;

import com.alex.homework4example.dto.CardDTO;
import com.alex.homework4example.entity.Card;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.dao.impl.CustomerDao;
import com.alex.homework4example.dao.impl.AccountDao;
import com.alex.homework4example.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CardMapper implements Mapper<Card, CardDTO> {

    private final CustomerDao customerDao;
    private final AccountDao accountDao;

    public CardMapper(CustomerDao customerDao, AccountDao accountDao) {
        this.customerDao = customerDao;
        this.accountDao = accountDao;
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
        Customer customer = customerDao.findById(cardDTO.getCustomerId()).orElseThrow();
        Account account = accountDao.findById(cardDTO.getAccountId()).orElseThrow();
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
