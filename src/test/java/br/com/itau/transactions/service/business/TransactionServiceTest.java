package br.com.itau.transactions.service.business;

import br.com.itau.transactions.model.Transaction;
import br.com.itau.transactions.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private TransactionRepository transactionRepository;

    @Test
    void whenReceiveValidTransactionSaveIt() {

        var transaction = Transaction.builder().build();

        when(transactionRepository.save(transaction)).thenReturn(transaction);

        transaction = transactionService.processTransaction(transaction);

        assertNotNull(transaction);
        verify(transactionRepository,times(1)).save(transaction);
    }

    @Test
    void whenRequestTransactionsFromAValidAccountShouldReturn() {
        when(transactionRepository.findByAccountNumber("accountNumber")).thenReturn(
                List.of(Transaction.builder().build(),Transaction.builder().build()));

        var transactions = transactionService.getAllTransactions("accountNumber");

        assertNotNull(transactions);
        verify(transactionRepository,times(1)).findByAccountNumber("accountNumber");
    }

    @Test
    void whenRequestBalanceFromAValidAccountShouldReturnBalance() {
        when(transactionRepository.findByAccountNumber("accountNumber")).thenReturn(
                List.of(Transaction.builder().transactionValue(BigDecimal.TEN).build(),
                        Transaction.builder().transactionValue(BigDecimal.TEN).build()));

        var balanceValue = transactionService.getBalance("accountNumber");

        assertNotNull(balanceValue);
        verify(transactionRepository,times(1)).findByAccountNumber("accountNumber");
        assertEquals(BigDecimal.valueOf(20),balanceValue);
    }

    @Test
    void whenRequestBalanceFromAValidAccountShouldReturnBalanceZero() {
        when(transactionRepository.findByAccountNumber("accountNumber")).thenReturn(List.of());

        var balanceValue = transactionService.getBalance("accountNumber");

        assertNotNull(balanceValue);
        verify(transactionRepository,times(1)).findByAccountNumber("accountNumber");
        assertEquals(BigDecimal.ZERO,balanceValue);
    }
}