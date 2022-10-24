package br.com.itau.transactions.controller;

import br.com.itau.transactions.model.Transaction;
import br.com.itau.transactions.payload.TransactionSQSEvent;
import br.com.itau.transactions.service.business.TransactionService;
import br.com.itau.transactions.service.messaging.sqs.SQSService;
import com.amazonaws.util.json.Jackson;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController("transaction")
@AllArgsConstructor
public class TransactionController {

    private TransactionService transactionService;
    private SQSService sqsService;

    @PostMapping
    public ResponseEntity createTransaction(@RequestBody Transaction transaction){
        try{
            transaction = transactionService.processTransaction(transaction);
            return ResponseEntity.created(URI.create("transaction")).body(transaction);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/sqs")
    public ResponseEntity createTransactionSqs(@RequestBody Transaction transaction){
        try{
            TransactionSQSEvent event = TransactionSQSEvent.from(transaction);
            sqsService.sendMessageToOrderFinisher(Jackson.toJsonString(event));
            return ResponseEntity.created(URI.create("transaction")).body(transaction);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity getAccountBalance(@RequestParam("accountNumber")String accountNumber){
        try{
            var balance = transactionService.getBalance(accountNumber);

            return ResponseEntity.ok().body(balance);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
