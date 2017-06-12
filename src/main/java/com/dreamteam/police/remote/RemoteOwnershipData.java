package com.dreamteam.police.remote;

import com.dreamteam.police.dto.OwnershipDto;
import com.dreamteam.police.model.Car;
import com.dreamteam.police.model.Ownership;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by Loci on 8-5-2017.
 */
@SpringComponent
public class RemoteOwnershipData {

    @Autowired
    Authentication authentication;

    private String baseUrl = "http://192.168.24.33:8080/dreamteam-administration/police/api/ownerships";

    @Async
    public CompletableFuture<List<Ownership>> getAllOwnerships() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<OwnershipDto[]> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.GET, new HttpEntity<>(authentication.getHeaders()), OwnershipDto[].class);
            List<OwnershipDto> ownershipsDtos = Arrays.asList(responseEntity.getBody());
            List<Ownership> ownerships = new ArrayList<>();

            ownershipsDtos.forEach(o -> {
                Ownership ownership = new Ownership();
                ownership.setOwned(new Car(o.getCarDTO().getId(), o.getCarDTO().getIcan(), o.getCarDTO().getVin(), o.getCarDTO().getLicensePlate()));
                ownership.setOwner(o.getCitizenDTO());
                ownership.setStartOwnership(o.getStartOwnership());
                ownerships.add(ownership);
            });

            HttpStatus status = responseEntity.getStatusCode();
            return CompletableFuture.completedFuture(ownerships);
        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
        }
        return CompletableFuture.completedFuture(new ArrayList<>());
    }

//    @Async
//    public CompletableFuture<List<Ownership>> getAllOwnerships() {
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Ownership[]> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.GET, new HttpEntity<>(authentication.getHeaders()), Ownership[].class);
//        List<Ownership> ownerships = Arrays.asList(responseEntity.getBody());
//
//        return CompletableFuture.completedFuture(ownerships);
//    }
}
