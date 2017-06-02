package com.dreamteam.police.remote;

import com.dreamteam.police.dto.CarDTO;
import com.dreamteam.police.model.Car;
import com.dreamteam.police.model.Citizen;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
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

    private String baseUrl = "http://192.168.24.33:8080/dreamteam-administration/api/police/";

    public Car getCarByICAN(String ICAN) {
        RestTemplate restTemplate = new RestTemplate();
        Car car = restTemplate.getForObject("ENTER URL HERE", Car.class);
        return car;
    }

    public Car getCarByID(long id) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        Car car = restTemplate.getForObject(String.format(baseUrl + "cars/%d", id), Car.class);

        URI uri = new URI(String.format(baseUrl + "cars/%d", id));
        ResponseEntity<String> pureJson = restTemplate.exchange(new RequestEntity<String>(HttpMethod.GET, uri), String.class);

        return car;
    }

    public List<Car> getCarsOfCitizen(Citizen citizen) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Car[]> responseEntity = restTemplate.getForEntity("URL HERE", Car[].class);
        List<Car> cars = Arrays.asList(responseEntity.getBody());
        HttpStatus status = responseEntity.getStatusCode();

        return cars;
    }

    public List<Car> findCarsByICAN(String ican) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CarDTO[]> responseEntity = restTemplate.getForEntity(baseUrl + "findcarsbyican?ican=" + ican, CarDTO[].class);
        List<CarDTO> carDTOS = Arrays.asList(responseEntity.getBody());
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

    public Citizen getCitizenById(long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("IPHERE:8080/api/police/citizens/%d", id);
        Citizen citizen = restTemplate.getForObject(url, Citizen.class);
        return citizen;
    }

    public List<Car> getTestCars() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Car[]> responseEntity = restTemplate.getForEntity(baseUrl + "cars", Car[].class);
        List<Car> cars = Arrays.asList(responseEntity.getBody());
        HttpStatus status = responseEntity.getStatusCode();

        return cars;
    }
}
