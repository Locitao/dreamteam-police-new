package com.dreamteam.police.service;

import com.dreamteam.police.model.Car;
import com.dreamteam.police.model.Citizen;
import com.dreamteam.police.model.Ownership;
import com.vaadin.spring.annotation.SpringComponent;

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

    private List<Car> cars;

    @PostConstruct
    void init() {
        cars = new ArrayList<>();
        cars = getTestCars();
    }

    /**
     * This method is purely meant to instantiate a few cars to test with.
     * @return
     */
    private List<Car> getTestCars() {
        //TODO: for testing, add some ownerships and shit
        Car car1 = new Car("asdf1234", "fakeVIN1", "asdf12");
        Car car2 = new Car("asdf1235", "fakeVIN2", "asdf13");
        Car car3 = new Car("asdf1236", "fakeVIN3", "asdf14");

        Citizen citizen = new Citizen("Yoko", "Ono");
        Citizen citizen1 = new Citizen("Test", "Fake");

        Ownership ownership = new Ownership();
        ownership.setStartOwnership(new Date());
        ownership.setOwner(citizen);
        Ownership ownership1 = new Ownership();
        ownership1.setStartOwnership(new Date());
        ownership1.setOwner(citizen1);

        List<Ownership> ownerships = new ArrayList<>();
        ownerships.add(ownership);
        ownerships.add(ownership1);

        List<Ownership> ownerships1 = new ArrayList<>();
        ownerships1.add(ownership1);

        car1.setOwnerships(ownerships);
        car2.setOwnerships(ownerships);
        car3.setOwnerships(ownerships1);

        List<Car> list = new ArrayList<>();
        list.add(car1);
        list.add(car2);
        list.add(car3);
        return list;
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }
}
