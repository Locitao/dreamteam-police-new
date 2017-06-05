package com.dreamteam.police.jms;

import com.dreamteam.police.service.ReportCarService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by loci on 28-5-17.
 */
@Component
public class Receiver {

    @Autowired
    private ReportCarService reportCarService;

    @JmsListener(destination = "StolenCarTopic")
    public void receiveMessage(Message message) {
        System.out.println("Received: " + message.toString());
        MessageHeaders headers = message.getHeaders();
        String clientId = (String) headers.get("jms_messageId");

        if (clientId.contains("loci") || clientId.contains("dreamteam")) {
            return;
        }

        String json = message.getPayload().toString();
        Gson gson = new Gson();
        StolenJmsDto stolenJmsDto = gson.fromJson(json, StolenJmsDto.class);
        reportCarService.reportStolenDtoFromJms(stolenJmsDto);
    }
}
