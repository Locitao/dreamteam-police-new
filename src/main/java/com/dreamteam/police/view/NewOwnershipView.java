package com.dreamteam.police.view;

import com.dreamteam.police.model.Car;
import com.dreamteam.police.model.Citizen;
import com.dreamteam.police.model.Ownership;
import com.dreamteam.police.security.SecuritySingleton;
import com.dreamteam.police.service.CarOwnershipService;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by loci on 9-5-17.
 */
@SpringView(name = NewOwnershipView.NEW_OWNERSHIP_VIEW)
@ViewScope
public class NewOwnershipView extends VerticalLayout implements View {

    public static final String NEW_OWNERSHIP_VIEW = "NEW_OWNERSHIP_VIEW";

    @Autowired
    private CarOwnershipService carOwnershipService;

    @Autowired
    private SecuritySingleton securitySingleton;

    private ListDataProvider<Ownership> ownershipListDataProvider;
    private List<Ownership> ownershipList;
    private ListDataProvider<Car> carListDataProvider;
    private List<Car> carList;
    private ListDataProvider<Citizen> citizenListDataProvider;
    private List<Citizen> citizenList;
    private Ownership selectedOwnership;

    /*
    UI Elements
     */
    private Grid<Ownership> ownershipGrid;
    private Grid<Car> carGrid;
    private Grid<Citizen> citizenGrid;


    @PostConstruct
    void init() {
        HorizontalLayout root = new HorizontalLayout();
        root.setSizeFull();

        initializeLists();

        root.addComponent(createOwnershipGrid());

        root.addComponent(createCarGrid());

        root.addComponent(createCitizenGrid());

        addComponent(root);
    }

    private void initializeLists() {
        ownershipList = new ArrayList<>();
        //carOwnershipService.getAllOwnerships(ownershipList);
        carList = new ArrayList<>();
        citizenList = new ArrayList<>();
    }

    private VerticalLayout createOwnershipGrid() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        ownershipGrid = new Grid<>();
        ownershipGrid.setCaption("Ownerships");

        if (ownershipList == null) {
            ownershipList = new ArrayList<>();
            ownershipListDataProvider = DataProvider.ofCollection(ownershipList);
            ownershipGrid.setDataProvider(ownershipListDataProvider);
            carOwnershipService.getAllOwnerships(ownershipList, ownershipListDataProvider);
        }

        if (ownershipList.isEmpty()) {
            ownershipListDataProvider = DataProvider.ofCollection(ownershipList);
            ownershipGrid.setDataProvider(ownershipListDataProvider);

            carOwnershipService.getAllOwnerships(ownershipList, ownershipListDataProvider);
            Notification.show("Please wait while data is loaded.", Notification.Type.WARNING_MESSAGE);
        }

        //ownershipGrid.addColumn(Ownership::getId).setCaption("ID");
        ownershipGrid.addColumn(Ownership::getStartOwnership).setCaption("Start of ownership");
        ownershipGrid.addColumn(Ownership::getEndOwnership).setCaption("End of ownership");

        ownershipGrid.addSelectionListener(event -> {
            Optional<Ownership> temp = event.getFirstSelectedItem();
            temp.ifPresent(ownership -> {
                selectedOwnership = ownership;
                updateCarAndCitizen();
            });
        });

        layout.addComponent(ownershipGrid);
        return layout;
    }

    private VerticalLayout createCarGrid() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        carGrid = new Grid<>();
        carGrid.setCaption("Car in ownership");
        if (selectedOwnership != null) {
            carList.add(selectedOwnership.getOwned());
        }

        carListDataProvider = DataProvider.ofCollection(carList);
        carGrid.setDataProvider(carListDataProvider);

        carGrid.addColumn(Car::getICAN).setCaption("ICAN");
        carGrid.addColumn(Car::getVIN).setCaption("VIN");
        carGrid.addColumn(Car::getColor).setCaption("Color");

        layout.addComponent(carGrid);

        return layout;
    }

    private VerticalLayout createCitizenGrid() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        citizenGrid = new Grid<>();
        citizenGrid.setCaption("Citizen in ownership");
        if (selectedOwnership != null) {
            citizenList.add(selectedOwnership.getOwner());
        }

        citizenListDataProvider = DataProvider.ofCollection(citizenList);
        citizenGrid.setDataProvider(citizenListDataProvider);

        citizenGrid.addColumn(Citizen::getFullName).setCaption("Name");

        layout.addComponent(citizenGrid);

        return layout;
    }

    private void updateCarAndCitizen() {
        carList.clear();
        citizenList.clear();

        if (selectedOwnership == null) {
            carListDataProvider = DataProvider.ofCollection(carList);
            citizenListDataProvider = DataProvider.ofCollection(citizenList);

            carGrid.setDataProvider(carListDataProvider);
            citizenGrid.setDataProvider(citizenListDataProvider);
            return;
        }

        carList.add(selectedOwnership.getOwned());
        citizenList.add(selectedOwnership.getOwner());

        carListDataProvider = DataProvider.ofCollection(carList);
        citizenListDataProvider = DataProvider.ofCollection(citizenList);

        carGrid.setDataProvider(carListDataProvider);
        citizenGrid.setDataProvider(citizenListDataProvider);
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
