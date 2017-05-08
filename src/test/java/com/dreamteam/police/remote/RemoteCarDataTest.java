package com.dreamteam.police.remote;

import com.dreamteam.police.model.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Loci on 8-5-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RemoteCarDataTest {

    @Autowired
    private RemoteCarData remoteCarData;

    @Test
    public void getTestCars() throws Exception {
        Car testCar = remoteCarData.getCarByID(1L);

        List<Car> testCars = remoteCarData.getTestCars();
        assertTrue("Did not receive test cars.", !testCars.isEmpty());

    }

}