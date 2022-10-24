package br.com.itau.transactions.service.business;

import br.com.itau.transactions.model.Transaction;
import br.com.itau.transactions.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private TransactionRepository transactionRepository;

    public Transaction processTransaction(final Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions(final String accountNumber){
        return transactionRepository.findByAccountNumber(accountNumber);
    }

    public BigDecimal getBalance(String accountNumber){
        return getAllTransactions(accountNumber).stream()
                .map(Transaction::getTransactionValue)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}

