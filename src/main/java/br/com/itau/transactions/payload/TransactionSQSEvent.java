package br.com.itau.transactions.payload;

import br.com.itau.transactions.model.Transaction;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionSQSEvent {
    private String agency;
    private String accountNumber;
    private char accountDigit;
    private BigDecimal transactionValue;

    public static TransactionSQSEvent from(Transaction transaction) {
        return TransactionSQSEvent.builder()
                .agency(transaction.getAgency())
                .accountNumber(transaction.getAccountNumber())
                .accountDigit(transaction.getAccountDigit())
                .transactionValue(transaction.getTransactionValue())
                .build();
    }
}
