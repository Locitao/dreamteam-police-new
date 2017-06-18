package com.dreamteam.police.service;

import com.dreamteam.police.dto.CarDTO;
import com.dreamteam.police.dto.StolenDTO;
import com.dreamteam.police.jms.Sender;
import com.dreamteam.police.jms.StolenJmsDto;

import com.dreamteam.police.model.Car;
import com.dreamteam.police.remote.RemoteCarData;
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
    private
    RemoteReporting remoteReporting;
    @Autowired
    RemoteCarData remoteCarData;
    @Autowired
    Sender sender;

    @Autowired
    private
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

        boolean isStolen = status.toLowerCase().equals("stolen");

        StolenJmsDto stolenJmsDto = new StolenJmsDto(car.getICAN(), car.getLicenceplate(), Instant.now().toEpochMilli(), isStolen);
        jmsReporting.sendMessage(stolenJmsDto);
    }

    public void reportStolenDtoFromJms(StolenJmsDto stolenJmsDto) {
        //jms message comes in, passes on to administration
        CarDTO carDTO = new CarDTO();
        carDTO.setIcan(stolenJmsDto.getIcan());
        carDTO.setLicensePlate(stolenJmsDto.getLicenseplate());
        String status = stolenJmsDto.getStolenValue() ? "stolen" : "found";
        StolenDTO stolenDTO = new StolenDTO(carDTO, status, "");
        remoteReporting.reportCar(stolenDTO);
    }
}
