package com.alex.homework4example.service.impl;

import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.exception.InsufficientFundsException;
import com.alex.homework4example.mapper.CommonMapper;
import com.alex.homework4example.repository.AbstractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AbstractRepository<Account> repository;

    @Mock
    private CommonMapper<Account, AccountDTO> mapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account fromAccount;
    private Account toAccount;

    @BeforeEach
    public void setUp() {
        fromAccount = new Account();
        fromAccount.setId(1L);
        fromAccount.setBalance(BigDecimal.valueOf(1000));

        toAccount = new Account();
        toAccount.setId(2L);
        toAccount.setBalance(BigDecimal.valueOf(500));
    }

    @Test
    void testCreate() {
        //given
        AccountDTO dto = new AccountDTO();
        Account account = new Account();
        when(mapper.toEntity(dto)).thenReturn(account);
        when(repository.create(account)).thenReturn(account);
        when(mapper.toDto(account)).thenReturn(dto);

        //when
        AccountDTO createdDto = accountService.create(dto);

        //then
        assertNotNull(createdDto);
        verify(mapper).toEntity(dto);
        verify(repository).create(account);
        verify(mapper).toDto(account);
    }

    @Test
    void testTransferMoney_Success() {
        //given
        when(repository.update(any(Account.class))).thenReturn(fromAccount);

        //when
        accountService.transferMoney(fromAccount, toAccount, BigDecimal.valueOf(200));

        //then
        assertEquals(BigDecimal.valueOf(800), fromAccount.getBalance());
        assertEquals(BigDecimal.valueOf(700), toAccount.getBalance());
        verify(repository).update(fromAccount);
        verify(repository).update(toAccount);
    }

    @Test
    void testTransferMoney_InsufficientFunds() {
        //when
        InsufficientFundsException exception = assertThrows(
                InsufficientFundsException.class,
                () -> accountService.transferMoney(fromAccount, toAccount, BigDecimal.valueOf(1200)));

        //then
        assertEquals("Not enough funds in the source account", exception.getMessage());
        verify(repository, never()).update(any(Account.class));
    }

    @Test
    void testFindById() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(mapper.toDto(fromAccount)).thenReturn(new AccountDTO());

        //when
        accountService.findDtoById(1L);

        //then
        verify(repository).findById(1L);
        verify(mapper).toDto(fromAccount);
    }

    @Test
    void testUpdate() {
        //given
        AccountDTO dto = new AccountDTO();
        Account account = new Account();
        account.setId(1L);
        when(repository.findById(account.getId())).thenReturn(Optional.of(account));
        when(repository.update(account)).thenReturn(account);
        when(mapper.toDto(account)).thenReturn(dto);


        AccountDTO updatedDto = accountService.update(account.getId(), dto);


        assertNotNull(updatedDto);
        verify(repository).update(account);
        verify(mapper).toDto(account);
    }

    @Test
    void testDeleteById() {
        //given
        when(repository.deleteById(1L)).thenReturn(true);

        //when
        boolean result = accountService.deleteById(1L);

        //then
        assertTrue(result);
        verify(repository).deleteById(1L);
    }

}
