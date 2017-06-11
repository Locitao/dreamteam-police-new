package com.dreamteam.police.view;

import com.dreamteam.police.jms.Sender;
import com.dreamteam.police.jms.StolenJmsDto;
import com.dreamteam.police.model.Car;
import com.dreamteam.police.model.Ownership;
import com.dreamteam.police.security.SecuritySingleton;
import com.dreamteam.police.service.CarService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Loci on 30-4-2017.
 */
@SpringView(name = SearchCarTrackerView.SEARCH_CAR_VIEW)
@ViewScope
public class SearchCarTrackerView extends VerticalLayout implements View {

    static final String SEARCH_CAR_VIEW = "SearchCarTracker";

    @Autowired
    private CarService carService;
    @Autowired
    private SecuritySingleton securitySingleton;

    private List<Car> cars;
    private ListDataProvider<Car> carListDataProvider;
    private Set<Car> selectedCars;
    private String searchString = "";

    //UI elements
    private Grid<Car> carGrid;

    @PostConstruct
    void init() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();

        initializeLists();

        //first create the layout with the search box + button
        VerticalLayout searchLayout = createSearchLayout();
        layout.addComponent(searchLayout);

        VerticalLayout gridLayout = createCarGrid();
        layout.addComponent(gridLayout);

        addComponent(layout);
    }

    private void initializeLists() {
        List<Ownership> ownerships = carService.getOwnerships();
        cars = ownerships.stream().map(Ownership::getOwned).collect(Collectors.toList());
    }

    private VerticalLayout createSearchLayout() {
        VerticalLayout search = new VerticalLayout();
        search.setSizeFull();

        TextField searchBox = new TextField("Enter ICAN");
        Button searchButton = new Button("Search for matching cars");
        searchButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        searchButton.addClickListener(e -> searchString = searchBox.getValue());
        searchButton.addClickListener(e -> updateCarDataProvider());
        //searchButton.addClickListener(e -> sender.sendMessage(new StolenJmsDto("asdf", "asdf", 82173512213L, true)));
        search.addComponent(searchBox);
        search.addComponent(searchButton);

        return search;
    }

    private void updateCarDataProvider() {
        List<Car> newCars = carService.searchCarsByIcan(searchString);
        carListDataProvider = DataProvider.ofCollection(newCars);
        carGrid.setDataProvider(carListDataProvider);
    }

    private VerticalLayout createCarGrid() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        carGrid = new Grid<>();
        carGrid.addColumn(Car::getICAN).setCaption("ICAN");
        carGrid.addColumn(Car::getVIN).setCaption("VIN");
        carGrid.addColumn(Car::getLicenceplate).setCaption("Licence plate");

        layout.addComponent(carGrid);
        return layout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        if (!securitySingleton.isLoggedIn(VaadinSession.getCurrent().getSession().getId())) {
            UI ui = UI.getCurrent();
            Navigator navigator = ui.getNavigator();
            navigator.navigateTo(LoginView.LOGIN_VIEW);
        }
        //constructed in init method
    }
}
