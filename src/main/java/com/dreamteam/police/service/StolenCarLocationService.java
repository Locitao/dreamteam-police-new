package com.dreamteam.police.service;

import com.dreamteam.police.jms.IcanCoordinateDTO;
import com.dreamteam.police.model.Coordinate;
import com.dreamteam.police.remote.RemoteTrackCar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
