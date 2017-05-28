package com.dreamteam.police.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * Created by loci on 28-5-17.
 */
@Component
public class Receiver {

    @JmsListener(destination = "StolenCarTopic")
    public void receiveMessage(Message message) {
        System.out.println("Received: " + message.toString());
    }
}
