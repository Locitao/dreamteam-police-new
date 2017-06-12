package com.dreamteam.police.remote;

import com.dreamteam.police.dto.CarDTO;
import com.dreamteam.police.model.Car;
import com.dreamteam.police.model.Citizen;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by loci on 2-5-17.
 */
@SpringComponent
public class RemoteCarData {

    @Autowired
    Authentication authentication;

    private String baseUrl = "http://192.168.24.33:8080/dreamteam-administration/police/api/";

    public List<Car> findCarsByICAN(String ican) {
        RestTemplate restTemplate = new RestTemplate();

        //ResponseEntity<CarDto[]> responseEntity = restTemplate.getForEntity(baseUrl + "findcarsbyican?ican=" + ican, CarDto[].class);
        List<CarDTO> carDTOS = new ArrayList<>();
        try {
            ResponseEntity<CarDTO[]> responseEntity = restTemplate.exchange(baseUrl + "findcarsbyican?ican=" + ican, HttpMethod.GET, new HttpEntity<>(authentication.getHeaders()), CarDTO[].class);
            carDTOS = Arrays.asList(responseEntity.getBody());
        } catch (HttpClientErrorException ex) {
            return new ArrayList<>();
        }
        List<Car> cars = new ArrayList<>();
        carDTOS.forEach(c -> {
            Car car = new Car();
            car.setId(c.getId());
            car.setICAN(c.getIcan());
            car.setVIN(c.getVin());
            car.setLicenceplate(c.getLicensePlate());
            car.setColor(c.getVehicleColor());
            car.setFuelType(c.getFuelType());
        });
        return cars;
    }
}
