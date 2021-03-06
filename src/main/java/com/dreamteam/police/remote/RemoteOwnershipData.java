package com.dreamteam.police.remote;

import com.dreamteam.police.dto.OwnershipDto;
import com.dreamteam.police.model.Car;
import com.dreamteam.police.model.Ownership;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Loci on 8-5-2017.
 */
@Service
public class RemoteOwnershipData {

    @Autowired
    private Authentication authentication;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String baseUrl = "http://192.168.24.33:8080/dreamteam-administration/police/api/ownerships";

    @Async
    public CompletableFuture<List<Ownership>> getAllOwnerships() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<OwnershipDto[]> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.GET, new HttpEntity<>(authentication.getHeaders()), OwnershipDto[].class);
            List<OwnershipDto> ownershipsDtos = Arrays.asList(responseEntity.getBody());
            List<Ownership> ownerships = new ArrayList<>();

            ownershipsDtos.forEach(o -> {
                Ownership ownership = new Ownership();
                ownership.setOwned(new Car(o.getCarDTO().getId(), o.getCarDTO().getIcan(), o.getCarDTO().getVin(), o.getCarDTO().getLicensePlate(), o.getCarDTO().getVehicleColor()));
                ownership.setOwner(o.getCitizenDTO());
                ownership.setStartOwnership(o.getStartOwnership());
                ownerships.add(ownership);
            });

            HttpStatus status = responseEntity.getStatusCode();
            System.out.println("Received ownerships");
            return CompletableFuture.completedFuture(ownerships);
        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
            logger.error("Could not connect to remote", ex);
        }
        return CompletableFuture.completedFuture(new ArrayList<>());
    }
}
