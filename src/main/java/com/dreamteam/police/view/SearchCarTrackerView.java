package com.dreamteam.police.view;

import com.dreamteam.police.model.Car;
import com.dreamteam.police.service.CarService;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by Loci on 30-4-2017.
 */
@SpringView(name = SearchCarTrackerView.SEARCH_CAR_VIEW)
@ViewScope
public class SearchCarTrackerView extends VerticalLayout implements View {

    static final String SEARCH_CAR_VIEW = "SearchCarTracker";

    @Autowired
    private CarService carService;

    private ListDataProvider<Car> cars;
    private Set<Car> selectedCars;

    @PostConstruct
    void init() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        TextField carTrackerTextBox = new TextField("Car tracker:");
        verticalLayout.addComponent(carTrackerTextBox);

        Button button = new Button("Filter by ICAN");
        button.setStyleName(ValoTheme.BUTTON_PRIMARY);
        verticalLayout.addComponent(button);

        layout.addComponent(verticalLayout);

        Grid<Car> carGrid = getGridOfCars();

        button.addClickListener(e -> {
            String temp = carTrackerTextBox.getValue();
            if (temp.isEmpty()) {
                cars.clearFilters();
                return;
            }

            cars.setFilter(Car::getICAN, ican -> ican.contains(temp));
        });

        VerticalLayout rightVerticalLayout = new VerticalLayout();
        rightVerticalLayout.addComponent(carGrid);
        rightVerticalLayout.setComponentAlignment(carGrid, Alignment.TOP_RIGHT);

        Button submitSelectedCars = new Button("Report car as stolen (SOON)");
        submitSelectedCars.setStyleName(ValoTheme.BUTTON_PRIMARY);
        submitSelectedCars.addClickListener(e -> doSomethingWithCars());

        rightVerticalLayout.addComponent(submitSelectedCars);
        rightVerticalLayout.setComponentAlignment(submitSelectedCars, Alignment.BOTTOM_RIGHT);

        layout.addComponent(rightVerticalLayout);
        addComponent(layout);
    }

    private Grid<Car> getGridOfCars() {
        Grid<Car> grid = new Grid<>();
        cars = DataProvider.ofCollection(carService.getCars());
        grid.setDataProvider(cars);
        grid.addColumn(Car::getICAN).setCaption("ICAN");
        grid.addColumn(Car::getVIN).setCaption("VIN");
        grid.addColumn(Car::getLicenceplate).setCaption("Licence plate");
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addSelectionListener(e -> selectedCars = e.getAllSelectedItems());

        return grid;
    }

    private void doSomethingWithCars() {
        if (selectedCars == null || selectedCars.isEmpty()) {
            Notification.show("Please select one or more cars before you press the button.");
            return;
        }

        Notification.show("Congratulations, you pressed the button.");
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        //constructed in init method
    }
}
