package com.dreamteam.police.view;

import com.dreamteam.police.model.Car;
import com.dreamteam.police.model.Ownership;
import com.dreamteam.police.service.CarService;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * Created by loci on 2-5-17.
 */
@SpringView(name = OwnershipView.OWNERSHIP_VIEW)
@ViewScope
public class OwnershipView extends VerticalLayout implements View {

    public static final String OWNERSHIP_VIEW = "Ownership";

    @Autowired
    private CarService carService;

    private ListDataProvider<Car> cars;
    private Car selectedCar;

    private ListDataProvider<Ownership> ownerships;
    private Ownership selectedOwnership;

    /*UI elements */
    private Grid<Ownership> ownershipGrid;

    @PostConstruct
    void init() {
        HorizontalLayout root = new HorizontalLayout();
        root.setSizeFull();

        root.addComponent(createCarGrid());
        root.addComponent(createOwnershipGrid());

        addComponent(root);
    }

    private VerticalLayout createCarGrid() {
        VerticalLayout carGridLayout = new VerticalLayout();

        Grid<Car> grid = new Grid<>();

        Car c = carService.getCars().get(0);
        cars = DataProvider.ofCollection(carService.getCars());
        grid.setDataProvider(cars);
        grid.addColumn(Car::getICAN).setCaption("ICAN");
        grid.addColumn(Car::getVIN).setCaption("VIN");
        grid.addColumn(Car::getLicenceplate).setCaption("Licence plate");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        //grid.addSelectionListener(e -> selectedCar = e.getFirstSelectedItem().isPresent() ? e.getFirstSelectedItem().get() : null);
        grid.addSelectionListener(e -> {
            Set<Car> selected = e.getAllSelectedItems();
            if (selected.isEmpty()) {
                //do nothing
            } else {

            }
        });
        grid.select(c);

        carGridLayout.addComponent(grid);

        return carGridLayout;
    }

    private VerticalLayout createOwnershipGrid() {
        VerticalLayout ownershipLayout = new VerticalLayout();

        ownershipGrid = new Grid<>();

        ownerships = DataProvider.ofCollection(selectedCar.getOwnerships());

        ownershipGrid.setDataProvider(ownerships);

        ownershipGrid.addColumn(o -> o.getOwner().getLastName()).setCaption("Owner");
        ownershipGrid.addColumn(Ownership::getStartOwnership).setCaption("Owned since");
        ownershipGrid.addColumn(o -> o.getEndOwnership() == null ? "Present" : o.getEndOwnership()).setCaption("Owned until");

        ownershipLayout.addComponent(ownershipGrid);

        return ownershipLayout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        //initialized in init()
    }
}
