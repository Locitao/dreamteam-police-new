package com.dreamteam.police.jms;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Loci on 2-6-2017.
 */
@Component
public class Sender {

    @Autowired
    ConfigurableApplicationContext context;

    public void sendMessage(StolenDto stolenDto) {
        JmsTemplate template = context.getBean(JmsTemplate.class);

        Gson gson = new Gson();
        String json = gson.toJson(stolenDto);
        template.convertAndSend("StolenCarTopic", json);
    }
}
