package com.dreamteam.police.service;

import com.dreamteam.police.dto.CarDto;
import com.dreamteam.police.dto.StatusDto;
import com.dreamteam.police.model.Car;
import com.dreamteam.police.remote.RemoteReporting;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by loci on 16-5-17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportCarServiceTest {

    @Mock
    private RemoteReporting remoteReporting;

    @InjectMocks
    @Resource
    private ReportCarService reportCarService;

    private Car validCar = new Car(1L,"1234", "123", "asdf12");
    private StatusDto validStatusDto = new StatusDto(new CarDto(validCar.getId(), validCar.getLicenceplate(), validCar.getVIN(), validCar.getICAN()), "asdf", "asdf");

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(remoteReporting.reportCar(validStatusDto)).thenReturn(true);
    }

    @Test
    public void reportCar() throws Exception {
        reportCarService.reportCar(validCar, validStatusDto.getStatus(), validStatusDto.getComment());
    }

}