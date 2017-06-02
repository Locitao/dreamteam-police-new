package com.dreamteam.police.service;

import com.dreamteam.police.dto.CarDTO;
import com.dreamteam.police.dto.StolenDTO;
import com.dreamteam.police.model.Car;
import com.dreamteam.police.remote.RemoteReporting;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Loci on 15-5-2017.
 */
@SpringComponent
public class ReportCarService {

    @Autowired
    RemoteReporting remoteReporting;

    /**
     * Depending on the given status, can report a car as stolen, found or other things.
     * Builds a carDto & stolenDto to send to the administration.
     * @param car
     * @param status
     * @param comment
     */
    public void reportCar(Car car, String status, String comment) {
        CarDTO carDTO = new CarDTO(car.getId(), car.getLicenceplate(), car.getVIN(), car.getICAN());
        StolenDTO stolenDTO = new StolenDTO(carDTO, status, comment);

        remoteReporting.reportCar(stolenDTO);
    }
}
