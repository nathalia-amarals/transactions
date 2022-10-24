package br.com.itau.transactions.service.business;

import br.com.itau.transactions.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class AccountService {

    private TransactionService transactionService;

    public BigDecimal getBalance(String accountNumber){
        return transactionService.getAllTransactions(accountNumber).stream()
                .map(Transaction::getTransactionValue)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
