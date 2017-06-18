package com.dreamteam.police.jms;

import com.dreamteam.police.service.ReportCarService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

/**
 * Created by loci on 28-5-17.
 */
@Component
public class Receiver {

    @Autowired
    private ReportCarService reportCarService;

    @JmsListener(destination = "StolenCarTopic", containerFactory = "myJmsContainerFactory")
    public void receiveMessage(Message message) {
        System.out.println("Received: " + message.toString());
        MessageHeaders headers = message.getHeaders();
        String clientId = (String) headers.get("jms_messageId");
        System.out.println("received json: " + message.getPayload().toString());

        if (clientId.contains("loci") || clientId.contains("dreamteam")) {
            return;
        }

        String json = message.getPayload().toString();
        System.out.println("received json: " + json);
        Gson gson = new Gson();
        StolenJmsDto stolenJmsDto;
        try {
            stolenJmsDto = gson.fromJson(json, StolenJmsDto.class);
        } catch (JsonSyntaxException ex) {
            System.out.println("Received bad message: " + message);
            return;
        }
        reportCarService.reportStolenDtoFromJms(stolenJmsDto);
    }
}
