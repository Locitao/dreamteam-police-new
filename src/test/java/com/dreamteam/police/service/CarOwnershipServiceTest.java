package com.dreamteam.police.service;

import com.dreamteam.police.model.Car;
import com.dreamteam.police.model.Ownership;
import com.dreamteam.police.remote.RemoteOwnershipData;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.when;

/**
 * Created by Loci on 18-6-2017.
 */
public class CarOwnershipServiceTest {

    @Mock
    private
    RemoteOwnershipData ownershipData;

    @InjectMocks
    @Resource
    private
    CarOwnershipService carOwnershipService;

    private final String validIcan = "1234";
    private ListDataProvider<Car> carListDataProvider;
    private List<Car> cars;
    private List<Ownership> ownerships;
    private ListDataProvider<Ownership> ownershipListDataProvider;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        cars = new ArrayList<>();
        carListDataProvider = DataProvider.ofCollection(cars);
        ownerships = new ArrayList<>();
        ownershipListDataProvider = DataProvider.ofCollection(ownerships);
        Car c = new Car(1L, validIcan, "1234", "asdf12");
        Ownership o = new Ownership();
        o.setOwned(c);
        ownerships.add(o);
        CompletableFuture<List<Ownership>> future = CompletableFuture.completedFuture(ownerships);

        when(ownershipData.getAllOwnerships()).thenReturn(future);
    }

    @Test
    public void searchCarsByIcan() throws Exception {
        carOwnershipService.searchCarsByIcan(validIcan, carListDataProvider, cars);
        assert(cars.size() > 0);
        assert(cars.get(0).getICAN().equals(validIcan));
    }

    @Test
    public void getAllOwnerships() throws Exception {
        carOwnershipService.getAllOwnerships(ownerships, ownershipListDataProvider);
        assert(ownerships.size() > 0);
        assert(ownerships.get(0).getOwned().getICAN().equals(validIcan));
    }

    @After
    public void tearDown() {
        carListDataProvider = null;
        cars = null;
        ownerships = null;
        ownershipListDataProvider = null;
    }
}