package com.dreamteam.police.service;

import com.dreamteam.police.model.Car;
import com.dreamteam.police.model.Citizen;
import com.dreamteam.police.model.Ownership;
import com.dreamteam.police.remote.RemoteCarData;
import com.dreamteam.police.remote.RemoteOwnershipData;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.UI;
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
    private RemoteOwnershipData remoteOwnershipData;

    private List<Ownership> ownerships;
    private ListDataProvider<Ownership> listDataProvider;

    private List<Car> cars;
    private ListDataProvider<Car> carListDataProvider;

    @PostConstruct
    void init() {
        ownerships = new ArrayList<>();
    }


    @Async
    public void searchCarsByIcan(String ICAN, ListDataProvider<Car> carListDataProvider, List<Car> cars) {
        this.carListDataProvider = carListDataProvider;
        this.cars = cars;

        if (ownerships == null) {
            ownerships = new ArrayList<>();
        }

        if (ownerships.isEmpty()) {
            getOwnershipsFromRemote();
        }
        cars.addAll(ownerships.stream()
                .map(Ownership::getOwned)
                .filter(c -> c.getICAN().contains(ICAN))
                .collect(Collectors.toList()));
        carListDataProvider.refreshAll();
    }

    @Async
    public void getAllOwnerships(List<Ownership> ownerships, ListDataProvider<Ownership> listDataProvider) {
        this.listDataProvider = listDataProvider;
        this.ownerships = ownerships;
        if (ownerships.isEmpty()) {
            getOwnershipsFromRemote();
        }
    }

    private void getOwnershipsFromRemote() {
        CompletableFuture<List<Ownership>> future = remoteOwnershipData.getAllOwnerships();
        boolean breaker = false;

        while (!breaker) {
            if (future.isDone()) {
                try {
                    ownerships.addAll(future.get());
                    System.out.println("Added future ownerships to ownerships");
                    if (listDataProvider != null) {
                        listDataProvider.refreshAll();
                    }
                    if (carListDataProvider != null) {
                        carListDataProvider.refreshAll();
                    }
                    breaker = true;
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
