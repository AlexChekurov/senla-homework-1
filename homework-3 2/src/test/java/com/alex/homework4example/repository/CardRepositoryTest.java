package com.alex.homework4example.repository;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.entity.Card;
import com.alex.homework4example.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Transactional
@Rollback
@SpringJUnitConfig(classes = { AppConfig.class, DataBaseConfig.class })
@TestPropertySource(locations = "classpath:application-test.properties")
class CardRepositoryTest {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        cardRepository.deleteAll();
        accountRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    void testCreateCard() {
        // when
        Card card = createTestCard();

        // then
        assertNotNull(card.getId(), "Card ID should not be null after creation");
    }

    @Test
    void testFindById() {
        // given
        Card card = createTestCard();

        // when
        Optional<Card> foundCard = cardRepository.findById(card.getId());

        // then
        assertTrue(foundCard.isPresent(), "Card should be found");
        assertEquals(card.getId(), foundCard.get().getId(), "The IDs should match");
    }

    @Test
    void testUpdateCard() {
        // given
        Card card = createTestCard();
        card.setCardType("MasterCard");
        cardRepository.update(card);

        // when
        Optional<Card> updatedCard = cardRepository.findById(card.getId());

        // then
        assertTrue(updatedCard.isPresent(), "Card should be found after update");
        assertEquals("MasterCard", updatedCard.get().getCardType(), "Card type should be updated");
    }

    @Test
    void testDeleteCard() {
        // given
        Card card = createTestCard();
        Optional<Card> foundCardBeforeDeletion = cardRepository.findById(card.getId());
        assertTrue(foundCardBeforeDeletion.isPresent(), "Card should exist before deletion");

        // when
        cardRepository.deleteById(card.getId());

        // then
        Optional<Card> deletedCard = cardRepository.findById(card.getId());
        assertFalse(deletedCard.isPresent(), "Card should be deleted and not found in the database");
    }

    @Test
    void testFindAllCards() {
        // given
        createTestCard();
        createTestCard();

        // when
        List<Card> cards = cardRepository.findAll();

        // then
        assertEquals(2, cards.size(), "There should be two cards in the database");
    }

    private Customer createTestCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail(UUID.randomUUID() + "@example.com");
        customer.setPhone(String.valueOf(new Random().nextInt()));
        customer.setAddress(null);
        customer.setUser(null);
        customerRepository.create(customer);
        return customer;
    }

    private Account createTestAccount() {
        Account account = new Account();
        account.setAccountNumber(UUID.randomUUID().toString().substring(0, 20));
        account.setAccountType("Savings");
        account.setBalance(BigDecimal.valueOf(1000.00));
        account.setCurrency("USD");
        account.setIban(UUID.randomUUID().toString().substring(0, 28));
        account.setCreatedAt(LocalDateTime.now());

        Customer customer = createTestCustomer();
        account.setCustomer(customer);

        accountRepository.create(account);
        return account;
    }

    private Card createTestCard() {
        Account account = createTestAccount();

        Card card = new Card();
        card.setCardNumber(UUID.randomUUID().toString().substring(0, 20));
        card.setCardType("Visa");
        card.setExpirationDate(LocalDate.now().plusYears(2));
        card.setCvv("123");
        card.setCreatedAt(LocalDate.from(LocalDateTime.now()));
        card.setAccount(account);

        cardRepository.create(card);
        return card;
    }

}
