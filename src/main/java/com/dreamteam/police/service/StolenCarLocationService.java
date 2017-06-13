package com.dreamteam.police.service;

import com.dreamteam.police.jms.IcanCoordinateDTO;
import com.dreamteam.police.model.Coordinate;
import com.dreamteam.police.remote.RemoteTrackCar;
import com.sun.xml.internal.ws.util.CompletedFuture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by Loci on 7-6-2017.
 */
@Component
public class StolenCarLocationService {

    @Autowired
    private RemoteTrackCar remoteTrackCar;

    private Map<String, List<Coordinate>> icans; //or iCan'ts?
    private ObservableList<Coordinate> coordinates;
    private String icanCurrentlyBeingTracked;

    @PostConstruct
    void init() {
        icans = new HashMap<>();
        icanCurrentlyBeingTracked = "";
        coordinates = FXCollections.observableArrayList();
    }

    public void addListOfCoordinates(List<IcanCoordinateDTO> icanCoordinateDTOS) {
        //unless something fucks up @ movreg, we don't need null checks here wololo
        List<Coordinate> coordinates = new ArrayList<>();

        icanCoordinateDTOS.forEach(i -> coordinates.add(new Coordinate(i.getLat(), i.getLng())));
        String ican = icanCoordinateDTOS.get(0).getICAN();

        icans.put(ican, coordinates);

        if (ican.equals(this.icanCurrentlyBeingTracked)) {
            this.coordinates.addAll(coordinates);
        }
    }

    public ObservableList<Coordinate> getCoordinatesOfIcan(String ICAN) {
        this.icanCurrentlyBeingTracked = ICAN;
        if (icans.containsKey(ICAN)) {
            if (!ICAN.equals(this.icanCurrentlyBeingTracked)) {
                coordinates = FXCollections.observableArrayList();
            }
            this.coordinates.addAll(icans.get(ICAN));
            return this.coordinates; //ugh, sorry
        } else {
            return this.coordinates;
        }
    }

    public boolean registerIcanAsStolenAtMovReg(String ICAN) {
        return remoteTrackCar.setRemoteTracking(ICAN);
    }

    @Async
    public void getLocationHistory(String ICAN, List<IcanCoordinateDTO> dtos) {
        try {
            System.out.println("Hashcode of dtos" + dtos.hashCode());
            fillCoordinateList(ICAN, dtos);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void fillCoordinateList(String ICAN, List<IcanCoordinateDTO> dtos) throws ExecutionException, InterruptedException {
        CompletableFuture<List<IcanCoordinateDTO>> future = remoteTrackCar.getLocationHistory(ICAN);

        System.out.println("Remote called from StolenCarLocationService");
        boolean breaker = false;
        while (!breaker) {
            if (future.isDone()) {
                System.out.println("Future is done, adding to list");
                System.out.println("Hashcode of dtos after future is done: " + dtos.hashCode());
                dtos.addAll(future.get());
                breaker = true;
            }

            if (future.isCancelled()) {
                breaker = true;
            }
        }
    }
}
