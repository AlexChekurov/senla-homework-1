package com.alex.homework4example.service.impl;

import com.alex.homework4example.dto.TransactionDTO;
import com.alex.homework4example.entity.Transaction;
import com.alex.homework4example.mapper.CommonMapper;
import com.alex.homework4example.repository.AbstractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @Mock
    private AbstractRepository<Transaction> transactionRepository;

    @Mock
    private CommonMapper<Transaction, TransactionDTO> transactionMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction transaction;
    private TransactionDTO transactionDTO;

    @BeforeEach
    public void setUp() {
        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(BigDecimal.valueOf(100.00));
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setCurrency("USD");

        transactionDTO = new TransactionDTO();
        transactionDTO.setId(1L);
        transactionDTO.setAmount(BigDecimal.valueOf(100.00));
        transactionDTO.setTransactionDate(transaction.getTransactionDate());
        transactionDTO.setCurrency("USD");
    }

    @Test
    public void testCreateTransaction() {
        when(transactionMapper.toEntity(any(TransactionDTO.class))).thenReturn(transaction);
        when(transactionRepository.create(any(Transaction.class))).thenReturn(transaction);
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionDTO);

        TransactionDTO createdTransactionDTO = transactionService.create(transactionDTO);

        assertNotNull(createdTransactionDTO);
        assertEquals(transactionDTO.getAmount(), createdTransactionDTO.getAmount());
        assertEquals(transactionDTO.getTransactionDate(), createdTransactionDTO.getTransactionDate());
        assertEquals(transactionDTO.getCurrency(), createdTransactionDTO.getCurrency());
        verify(transactionMapper).toEntity(transactionDTO);
        verify(transactionRepository).create(transaction);
        verify(transactionMapper).toDto(transaction);
    }

    @Test
    public void testFindById() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        when(transactionMapper.toDto(transaction)).thenReturn(transactionDTO);

        Optional<TransactionDTO> foundTransactionDTO = transactionService.findDtoById(1L);

        assertTrue(foundTransactionDTO.isPresent());
        assertEquals(transactionDTO.getId(), foundTransactionDTO.get().getId());
        verify(transactionRepository).findById(1L);
    }

    @Test
    public void testUpdateTransaction() {
        when(transactionMapper.toEntity(any(TransactionDTO.class))).thenReturn(transaction);
        when(transactionRepository.update(any(Transaction.class))).thenReturn(transaction);
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionDTO);

        TransactionDTO updatedTransactionDTO = transactionService.update(transactionDTO);

        assertNotNull(updatedTransactionDTO);
        assertEquals(transactionDTO.getAmount(), updatedTransactionDTO.getAmount());
        assertEquals(transactionDTO.getTransactionDate(), updatedTransactionDTO.getTransactionDate());
        assertEquals(transactionDTO.getCurrency(), updatedTransactionDTO.getCurrency());
        verify(transactionMapper).toEntity(transactionDTO);
        verify(transactionRepository).update(transaction);
        verify(transactionMapper).toDto(transaction);
    }

    @Test
    public void testDeleteById() {
        when(transactionRepository.deleteById(1L)).thenReturn(true);

        boolean result = transactionService.deleteById(1L);

        assertTrue(result);
        verify(transactionRepository).deleteById(1L);
    }

    @Test
    public void testFindAll() {
        when(transactionRepository.findAll()).thenReturn(List.of(transaction));
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionDTO);

        List<TransactionDTO> allTransactions = transactionService.findAll();

        assertEquals(1, allTransactions.size());
        assertEquals(transactionDTO.getAmount(), allTransactions.get(0).getAmount());
        assertEquals(transactionDTO.getTransactionDate(), allTransactions.get(0).getTransactionDate());
        assertEquals(transactionDTO.getCurrency(), allTransactions.get(0).getCurrency());
        verify(transactionRepository).findAll();
    }
}
