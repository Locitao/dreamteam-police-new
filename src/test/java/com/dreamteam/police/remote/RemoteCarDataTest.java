package com.dreamteam.police.remote;

import com.dreamteam.police.model.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Loci on 8-5-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RemoteCarDataTest {

    //TODO: change to mock? now they fail when Administration is offline
    @Autowired
    private RemoteCarData remoteCarData;

    @Test
    public void getTestCars() throws Exception {
        //return true;
    }

}