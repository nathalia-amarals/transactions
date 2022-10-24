package br.com.itau.transactions.service.messaging.sqs;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class SQSService {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessageToOrderFinisher (String message){
        try{
            jmsTemplate.convertAndSend("itau-account-transaction-queue", message);
        }catch (Exception e){
            throw e;
        }
    }
}
