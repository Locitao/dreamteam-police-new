package com.dreamteam.police.service;

import com.dreamteam.police.model.Car;
import com.dreamteam.police.model.Citizen;
import com.dreamteam.police.remote.RemoteCarData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Loci on 2-5-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTest {

    @Mock
    private RemoteCarData remoteCarData;

    @InjectMocks
    @Resource
    private CarService carService;

    private String validICAN = "NL 1234 AB";
    private Citizen validCitizen = new Citizen("jan", "jansen");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(remoteCarData.getCarByICAN(validICAN)).thenReturn(new Car(validICAN, "VIN1234", "ASDF12"));

        when(remoteCarData.getCarsOfCitizen(validCitizen)).thenReturn(new ArrayList<>());
    }

    @Test
    public void getAllStolenCars() throws Exception {
    }

    @Test
    public void findCarByICAN() throws Exception {
        Car c = carService.findCarByICAN(validICAN);
        assertEquals("VIN number did not match", "VIN1234", c.getVIN());
    }

    @Test
    public void postCarAsStolen() throws Exception {
    }

}