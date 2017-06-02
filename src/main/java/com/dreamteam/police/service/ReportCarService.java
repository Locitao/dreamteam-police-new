package com.dreamteam.police.service;

import com.dreamteam.police.dto.CarDTO;
import com.dreamteam.police.dto.StolenDTO;
import com.dreamteam.police.jms.Sender;
import com.dreamteam.police.jms.StolenDto;
import com.dreamteam.police.model.Car;
import com.dreamteam.police.remote.RemoteReporting;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

/**
 * Created by Loci on 15-5-2017.
 */
@SpringComponent
public class ReportCarService {

    @Autowired
    RemoteReporting remoteReporting;

    @Autowired
    Sender jmsReporting;

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

        boolean isStolen = status.equals("stolen");

        StolenDto stolenDto = new StolenDto(car.getICAN(), car.getLicenceplate(), Instant.now().getEpochSecond(), isStolen);
        jmsReporting.sendMessage(stolenDto);
    }

    public void reportStolenDtoFromJms(StolenDto stolenDto) {
        CarDTO carDTO = new CarDTO();
        carDTO.setIcan(stolenDto.getIcan());
        carDTO.setLicensePlate(stolenDto.getLicensePlate());
        String status = stolenDto.getStolenValue() ? "stolen" : "found";
        StolenDTO stolenDTO = new StolenDTO(carDTO, status, "");
        remoteReporting.reportCar(stolenDTO);
    }
}
