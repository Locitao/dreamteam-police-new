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
public class CarOwnershipService {

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

    private void getOwnershipsFromRemote() {
        CompletableFuture<List<Ownership>> future = remoteOwnershipData.getAllOwnerships();
        boolean breaker = false;

        while (!breaker) {
            if (future.isDone()) {
                try {
                    ownerships.addAll(future.get());
                    System.out.println("Added future ownerships to ownerships");
                    System.out.println("Hashcode of ownershiplist to which data was added: " + ownerships.hashCode());
                    breaker = true;
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public List<Car> searchCarsByIcan(String ICAN) {
        if (ownerships.isEmpty()) {
            getOwnershipsFromRemote();
        }
        return ownerships.stream()
                .map(Ownership::getOwned)
                .filter(c -> c.getICAN().contains(ICAN))
                .collect(Collectors.toList());
    }

    //@Async
    public void getAllOwnerships(List<Ownership> ownerships) {
        this.ownerships = ownerships;
        if (ownerships.isEmpty()) {
            getOwnershipsFromRemote();
        }
    }
}
