package com.dreamteam.police.service;

import com.dreamteam.police.dto.CarDto;
import com.dreamteam.police.dto.StatusDto;
import com.dreamteam.police.jms.StolenJmsDto;
import com.dreamteam.police.model.Car;
import com.dreamteam.police.remote.RemoteCarData;
import com.dreamteam.police.remote.RemoteReporting;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Loci on 15-5-2017.
 */
@SpringComponent
public class ReportCarService {

    @Autowired
    RemoteReporting remoteReporting;
    @Autowired
    RemoteCarData remoteCarData;

    /**
     * Depending on the given status, can report a car as stolen, found or other things.
     * Builds a carDto & stolenDto to send to the administration.
     * @param car
     * @param status
     * @param comment
     */
    public void reportCar(Car car, String status, String comment) {
        CarDto carDto = new CarDto(car.getId(), car.getLicenceplate(), car.getVIN(), car.getICAN());
        StatusDto statusDto = new StatusDto(carDto, status, comment);

        remoteReporting.reportCar(statusDto);
    }

    public void reportCar(StolenJmsDto stolenJmsDto) {
        //first find the car with the given ican,
        //then build a new StatusDto, which gets send to administration
        List<Car> cars = remoteCarData.findCarsByICAN(stolenJmsDto.getIcan());
        String status = stolenJmsDto.isStolenvalue() ? "stolen" : "found";
        if (cars.isEmpty()) {
            CarDto carDto = new CarDto(null, stolenJmsDto.getLicenseplate(), null, stolenJmsDto.getIcan());
            StatusDto statusDto = new StatusDto(carDto, status, "");
            remoteReporting.reportCar(statusDto);
            return;
        }

        Car foundCar = cars.get(0);
        CarDto carDto = new CarDto(foundCar.getId(), foundCar.getLicenceplate(), foundCar.getVIN(), foundCar.getICAN());
        StatusDto statusDto = new StatusDto(carDto, status, "");
        remoteReporting.reportCar(statusDto);
    }
}
