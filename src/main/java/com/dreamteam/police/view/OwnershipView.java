package com.dreamteam.police.view;

import com.dreamteam.police.model.Car;
import com.dreamteam.police.model.Ownership;
import com.dreamteam.police.service.CarService;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.DateRenderer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.security.acl.Owner;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by loci on 2-5-17.
 */
@SpringView(name = OwnershipView.OWNERSHIP_VIEW)
@ViewScope
public class OwnershipView extends VerticalLayout implements View {

    public static final String OWNERSHIP_VIEW = "Ownership";

    @Autowired
    private CarService carService;

    private Car selectedCar;

    private Ownership selectedOwnership;

    /*UI elements */
    private Grid<Car> carGrid;
    private List<Car> cars;
    private ListDataProvider<Car> carDataProvider;

    private Grid<Ownership> ownershipGrid;
    private List<Ownership> ownerships;
    private ListDataProvider<Ownership> ownershipDataProvider;

    @PostConstruct
    void init() {
        HorizontalLayout root = new HorizontalLayout();
        root.setSizeFull();

        cars = new ArrayList<>();
        ownerships = new ArrayList<>();

        initializeLists();


        root.addComponent(createCarGrid());
        root.addComponent(createOwnershipGrid());

        addComponent(root);
    }

    private void initializeLists() {
        ownerships = carService.getOwnerships();
        ownerships.forEach(o -> cars.add(o.getOwned()));
    }

    private VerticalLayout createCarGrid() {
        VerticalLayout carGridLayout = new VerticalLayout();

        Grid<Car> grid = new Grid<>();

        carDataProvider = DataProvider.ofCollection(cars);
        grid.setDataProvider(carDataProvider);
        grid.addColumn(Car::getICAN).setCaption("ICAN");
        grid.addColumn(Car::getVIN).setCaption("VIN");
        grid.addColumn(Car::getLicenceplate).setCaption("Licence plate");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addSelectionListener(this::updateOwnershipGrid);

        carGridLayout.addComponent(grid);

        return carGridLayout;
    }

    private void updateOwnershipGrid(SelectionEvent<Car> event) {
        Optional<Car> car = event.getFirstSelectedItem();
        if (car.isPresent()) {
            ownerships = carService.getOwnerships().stream()
                .filter(o -> o.getOwned().equals(car.get()))
                .collect(Collectors.toList());
        } else {
            ownerships.clear();
        }
        /*
        Explanation for the situation below:
        ListDataProvider.refreshAll seems to be broken? For a grid to receive new data,
        we need to create a new DataProvider and set the grids DataProvider to this new instance.
         */
        ownershipDataProvider = DataProvider.ofCollection(ownerships);

        ownershipGrid.setDataProvider(ownershipDataProvider);
    }

    private VerticalLayout createOwnershipGrid() {
        VerticalLayout ownershipLayout = new VerticalLayout();

        ownershipGrid = new Grid<>();

        ownershipDataProvider = DataProvider.ofCollection(ownerships);

        ownershipGrid.setDataProvider(ownershipDataProvider);

        ownershipGrid.addColumn(o -> o.getOwner().getFullName()).setCaption("Owner");
        ownershipGrid.addColumn(Ownership::getStartOwnership, new DateRenderer("%1$tB %1$te, %1$tY", Locale.ENGLISH)).setCaption("Owned since");
        ownershipGrid.addColumn(o -> o.getEndOwnership() == null ? "Present" : o.getEndOwnership()).setCaption("Owned until");

        ownershipLayout.addComponent(ownershipGrid);

        return ownershipLayout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        //initialized in init()
    }
}
