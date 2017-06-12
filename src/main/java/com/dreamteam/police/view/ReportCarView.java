package com.dreamteam.police.view;

import com.dreamteam.police.model.Car;
import com.dreamteam.police.security.SecuritySingleton;
import com.dreamteam.police.service.CarService;
import com.dreamteam.police.service.ReportCarService;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Loci on 15-5-2017.
 */
@SpringView(name = ReportCarView.REPORT_CAR_VIEW)
@ViewScope
public class ReportCarView extends VerticalLayout implements View {

    public static final String REPORT_CAR_VIEW = "report_car";

    @Autowired
    private CarService carService;

    @Autowired
    private ReportCarService reportCarService;

    @Autowired
    private SecuritySingleton securitySingleton;

    private List<Car> cars;
    private ListDataProvider<Car> dataProvider;
    private Car selectedCar;
    private String selectedStatus;

    //UI elements
    private Grid<Car> carGrid;

    @PostConstruct
    void init() {
        HorizontalLayout root = new HorizontalLayout();
        root.setSizeFull();

        cars = new ArrayList<>();

        root.addComponent(createSearchLayout());
        root.addComponent(createCarGridLayout());
        root.addComponent(createStatusLayout());

        addComponent(root);
    }

    private VerticalLayout createSearchLayout() {
        VerticalLayout layout = new VerticalLayout();

        TextField searchBox = new TextField();
        layout.addComponent(searchBox);

        Button searchButton = new Button("Search by ICAN");
        searchButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        searchButton.addClickListener(e -> updateCarGrid(searchBox.getValue()));
        layout.addComponent(searchButton);

        return layout;
    }

    private VerticalLayout createCarGridLayout() {
        VerticalLayout layout = new VerticalLayout();
        carGrid = new Grid<>();

        carGrid.addColumn(Car::getICAN).setCaption("ICAN");
        carGrid.addColumn(Car::getVIN).setCaption("VIN");
        carGrid.addColumn(Car::getLicenceplate).setCaption("Licence plate");

        carGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        carGrid.addSelectionListener(e -> {
            Set<Car> selectedCars = carGrid.getSelectedItems();
            if (selectedCars.isEmpty()) {
                selectedCar = null;
            } else {
                selectedCar = selectedCars.iterator().next();
            }
        });

        layout.addComponent(carGrid);
        return layout;
    }

    private VerticalLayout createStatusLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        NativeSelect<String> statusList = new NativeSelect<>("Status");
        statusList.setItems("Stolen", "Found", "Other");

        statusList.addSelectionListener(e -> {
            Optional<String> selected = statusList.getSelectedItem();
            selectedStatus = selected.orElse("");
        });
        layout.addComponent(statusList);

        TextArea commentArea = new TextArea();
        layout.addComponent(commentArea);

        Button button = new Button("Post status of car");
        button.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        button.addClickListener(e -> {
            postCarAsStolen(selectedStatus, commentArea.getValue());
        });
        layout.addComponent(button);

        return layout;
    }

    private void updateCarGrid(String search) {
        cars = carService.searchCarsByIcan(search);
        if (cars.isEmpty()) {
            Notification.show("No cars found.", Notification.Type.ASSISTIVE_NOTIFICATION);
        }
        dataProvider = DataProvider.ofCollection(cars);
        carGrid.setDataProvider(dataProvider);
    }

    private void postCarAsStolen(String status, String comment) {
        if (status == null || status.isEmpty()) {
            Notification.show("Please select a status.", Notification.Type.ERROR_MESSAGE);
            return;
        }

        if (selectedCar == null) {
            Notification.show("Please select a car.", Notification.Type.ERROR_MESSAGE);
            return;
        }

        reportCarService.reportCar(selectedCar, status, comment);
        Notification.show("Reported " + selectedCar.getICAN() + " as " + status, Notification.Type.HUMANIZED_MESSAGE);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        if (!securitySingleton.isLoggedIn(VaadinSession.getCurrent().getSession().getId())) {
            UI ui = UI.getCurrent();
            Navigator navigator = ui.getNavigator();
            navigator.navigateTo(LoginView.LOGIN_VIEW);
        }
        //initialized in init method
    }
}
