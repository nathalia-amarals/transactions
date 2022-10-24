package br.com.itau.transactions.consumer;

import br.com.itau.transactions.model.Transaction;
import br.com.itau.transactions.payload.TransactionSQSEvent;
import br.com.itau.transactions.service.business.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;

@Slf4j
@RequiredArgsConstructor
public class TransactionSQSListener {

    private TransactionService transactionService;

    @JmsListener(destination = "itau-account-transaction-queue")
    public void listen(String event) throws JsonProcessingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            final var transactionPayload = objectMapper.readValue(event, TransactionSQSEvent.class);

            var transaction = Transaction.from(transactionPayload);

            transactionService.processTransaction(transaction);

        } catch (Exception e){
            log.error("transaction processing error {}",e.getCause());
            throw e;
        }
    }
}
