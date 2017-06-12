package com.dreamteam.police.service;

import com.dreamteam.police.model.Car;
import com.dreamteam.police.model.Citizen;
import com.dreamteam.police.model.Ownership;
import com.dreamteam.police.remote.RemoteCarData;
import com.dreamteam.police.remote.RemoteOwnershipData;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.PostConstruct;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Created by Loci on 1-5-2017.
 */
@SpringComponent
public class CarService {

    @Autowired
    private RemoteCarData remoteCarData;

    @Autowired
    private RemoteOwnershipData remoteOwnershipData;

    private List<Ownership> ownerships;

    @PostConstruct
    void init() {
        ownerships = new ArrayList<>();
        //ownerships = getTestCars();
        /*
        Only for demo purposes; will be reworked into ownership service
         */
        //ownerships = remoteOwnershipData.getAllOwnerships();
    }

    public List<Car> getAllStolenCars() {
        return new ArrayList<>();
    }

    public Car findCarByICAN(String ICAN) {
        return new Car("NL 1234 AB", "VIN1234", "ASDF12");
    }


    private void getOwnershipsFromRemote() {
        CompletableFuture<List<Ownership>> future = remoteOwnershipData.getAllOwnerships();
        boolean breaker = false;

        while (!breaker) {
            if (future.isDone()) {
                try {
                    this.ownerships = future.get();
                    breaker = true;
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Car> searchCarsByIcan(String ICAN) {
        return ownerships.stream()
                .map(Ownership::getOwned)
                .filter(c -> c.getICAN().contains(ICAN))
                .collect(Collectors.toList());
    }

    @Async
    public List<Ownership> getAllOwnerships() {
        if (ownerships.isEmpty()) {
            getOwnershipsFromRemote();
        }

        return ownerships;
    }

//    public List<Ownership> getOwnerships() {
//        return Collections.unmodifiableList(ownerships);
//    }
}
