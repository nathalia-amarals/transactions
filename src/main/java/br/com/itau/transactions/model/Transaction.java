package br.com.itau.transactions.model;

import br.com.itau.transactions.payload.TransactionSQSEvent;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@Document
public class Transaction {
    private String agency;
    private String accountNumber;
    private char accountDigit;
    private BigDecimal transactionValue;

    public static Transaction from (final TransactionSQSEvent transactionSQSEvent){
        return Transaction.builder()
                .agency(transactionSQSEvent.getAgency())
                .accountNumber(transactionSQSEvent.getAccountNumber())
                .accountDigit(transactionSQSEvent.getAccountDigit())
                .transactionValue(transactionSQSEvent.getTransactionValue())
                .build();
    }
}
