package com.dreamteam.police.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by loci on 30-5-17.
 */
@Component
public class Sender {

    @Autowired
    ConfigurableApplicationContext context;

    public void sendMessage(String message) {
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

        jmsTemplate.convertAndSend("StolenCarTopic", message);
    }
}
