package com.dreamteam.police.remote;

import com.dreamteam.police.model.Car;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.web.client.RestTemplate;

/**
 * Created by loci on 2-5-17.
 */
@SpringComponent
public class RemoteCarData {

    public Car getCarByICAN(String ICAN) {
        RestTemplate restTemplate = new RestTemplate();
        Car car = restTemplate.getForObject("ENTER URL HERE", Car.class);
        return car;
    }
}
