package com.dreamteam.police.jms;

import com.dreamteam.police.service.StolenCarLocationService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Loci on 7-6-2017.
 */
@Component
public class IcanCoordinateReceiver {

    @Autowired
    StolenCarLocationService stolenCarLocationService;

    @JmsListener(destination = "police.stolen.car.coordinates", containerFactory = "jmsListenerContainerQueue")
    public void receiveMessage(Message message) {
        String json = message.getPayload().toString();

        /*
        Quick and easy hack to get a list of objects from json with gson.
        Blame stackoverflow if it breaks stuff.
         */
        List<IcanCoordinateDTO> dtos = new Gson().fromJson(json, new TypeToken<List<IcanCoordinateDTO>>(){}.getType());

        stolenCarLocationService.addListOfCoordinates(dtos);
    }
}
