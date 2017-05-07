package com.dreamteam.police.service;

import com.dreamteam.police.model.Car;
import com.dreamteam.police.model.Citizen;
import com.dreamteam.police.model.Ownership;
import com.dreamteam.police.remote.RemoteCarData;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Loci on 1-5-2017.
 */
@SpringComponent
public class CarService {

    @Autowired
    private RemoteCarData remoteCarData;

    private List<Ownership> ownerships;

    @PostConstruct
    void init() {
        ownerships = new ArrayList<>();
        ownerships = getTestCars();
    }

    public List<Car> getAllStolenCars() {
        return new ArrayList<>();
    }

    public Car findCarByICAN(String ICAN) {
        return new Car("NL 1234 AB", "VIN1234", "ASDF12");
    }

    public boolean postCarAsStolen(Car car) {
        return true;
    }

    /**
     * This method is purely meant to instantiate a few cars to test with.
     * @return
     */
    private List<Ownership> getTestCars() {
        //TODO: for testing, add some ownerships and shit
        Car car1 = new Car("asdf1234", "fakeVIN1", "asdf12");
        Car car2 = new Car("asdf1235", "fakeVIN2", "asdf13");
        Car car3 = new Car("asdf1236", "fakeVIN3", "asdf14");

        Citizen citizen = new Citizen("Yoko", "Ono");
        Citizen citizen1 = new Citizen("Test", "Fake");

        Ownership ownership = new Ownership();
        ownership.setStartOwnership(new Date());
        ownership.setOwner(citizen);
        ownership.setOwned(car1);
        Ownership ownership1 = new Ownership();
        ownership1.setStartOwnership(new Date());
        ownership1.setOwner(citizen1);
        ownership1.setOwned(car2);

        List<Ownership> ownerships = new ArrayList<>();
        ownerships.add(ownership);
        ownerships.add(ownership1);

        return ownerships;
    }

    public List<Ownership> getOwnerships() {
        return Collections.unmodifiableList(ownerships);
    }
}
