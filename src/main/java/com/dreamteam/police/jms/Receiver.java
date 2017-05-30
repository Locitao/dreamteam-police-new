package com.dreamteam.police.jms;

import com.google.gson.Gson;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * Created by loci on 28-5-17.
 */
@Component
public class Receiver {

    @JmsListener(destination = "StolenCarTopic")
    public void receiveMessage(Message message) throws JMSException {
        String payload = message.getPayload().toString();
        Gson gson = new Gson();
        StolenJmsDto stolenJmsDto = gson.fromJson(payload, StolenJmsDto.class);
        System.out.println("Received: " + stolenJmsDto.toString());
    }
}
