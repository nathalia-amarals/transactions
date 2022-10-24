package br.com.itau.transactions.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class TransactionProducer {
    @Autowired
    private JmsTemplate jmsTemplate;

    private void sendMessageToOrderFinisher (String message){
        try{
            jmsTemplate.convertAndSend("itau-account-transaction-queue", message);
        }catch (Exception e){
            throw e;
        }
    }
}
