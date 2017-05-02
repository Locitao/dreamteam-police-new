package com.dreamteam.police.remote;

import com.dreamteam.police.model.Car;
import com.dreamteam.police.model.Citizen;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

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

    public List<Car> getCarsOfCitizen(Citizen citizen) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Car[]> responseEntity = restTemplate.getForEntity("URL HERE", Car[].class);
        List<Car> cars = Arrays.asList(responseEntity.getBody());
        HttpStatus status = responseEntity.getStatusCode();

        return cars;
    }
}
