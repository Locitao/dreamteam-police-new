package com.dreamteam.police.jms;

import com.google.gson.Gson;
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

    public void sendMessage(StolenJmsDto stolenJmsDto) {
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

        String message = new Gson().toJson(stolenJmsDto);

        jmsTemplate.convertAndSend("StolenCarTopic", message);
    }
}
