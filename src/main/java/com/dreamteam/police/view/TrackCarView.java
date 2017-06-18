package com.dreamteam.police.view;

import com.dreamteam.police.jms.IcanCoordinateDTO;
import com.dreamteam.police.model.Coordinate;
import com.dreamteam.police.security.SecuritySingleton;
import com.dreamteam.police.service.StolenCarLocationService;
import com.google.gson.Gson;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Loci on 6-6-2017.
 */
@SpringView(name = TrackCarView.TRACK_CAR)
@ViewScope
public class TrackCarView extends VerticalLayout implements View {
    static final String TRACK_CAR="track_car";

    @Autowired
    private StolenCarLocationService stolenCarLocationService;
    @Autowired
    private SecuritySingleton securitySingleton;

    private final String apiKey = "obviouslyfake";

    private GoogleMap googleMap;
    private GoogleMapPolyline lastLine;

    @PostConstruct
    void init() {
        VerticalLayout root = new VerticalLayout();
        //root.setSizeFull();
        googleMap = new GoogleMap(this.apiKey, null, "english");
        googleMap.setSizeFull();
        googleMap.setHeight("600px");

        root.addComponent(getSearchLayout());


        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(googleMap);
        root.addComponent(layout);
        addComponent(root);
    }

    private HorizontalLayout getSearchLayout() {
        HorizontalLayout layout = new HorizontalLayout();

        TextField field = new TextField("Enter full ICAN of car to track");
        layout.addComponent(field);

        Button trackCar = new Button("Live track the entered ICAN");
        trackCar.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        trackCar.addClickListener(e -> {
            if (!field.isEmpty()) {
                trackIcan(field.getValue());
                generateTestJson(field.getValue());
            } else {
                Notification.show("Enter an ICAN if you want to see something.", Notification.Type.ERROR_MESSAGE);
            }
        });
        layout.addComponent(trackCar);

        Button locationHistoryButton = new Button("Get location history of entered ICAN");
        locationHistoryButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        locationHistoryButton.addClickListener(e -> {
            if (!field.isEmpty()) {
                try {
                    updateMapWithLocationHistory(field.getValue());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            } else {
                Notification.show("Enter an ICAN if you want to see something.", Notification.Type.ERROR_MESSAGE);
            }
        });
        layout.addComponent(locationHistoryButton);

        return layout;
    }

    private void generateTestJson(String ICAN) {
        Gson gson = new Gson();
        IcanCoordinateDTO dto = new IcanCoordinateDTO(ICAN, 50.5, 50.4);
        IcanCoordinateDTO dto2 = new IcanCoordinateDTO(ICAN, 50.5, 50.4);
        IcanCoordinateDTO dto3 = new IcanCoordinateDTO(ICAN, 50.5, 50.5);
        List<IcanCoordinateDTO> dtos = new ArrayList<>();
        dtos.add(dto);
        dtos.add(dto2);
        dtos.add(dto3);
        System.out.println(gson.toJson(dtos));
    }

    private void trackIcan(String ICAN) {
        boolean succes = stolenCarLocationService.registerIcanAsStolenAtMovReg(ICAN);
        if (!succes) {
            Notification.show("Could not register ICAN as stolen at the remote server; live tracking will not work.", Notification.Type.ERROR_MESSAGE);
        }
        ObservableList<Coordinate> coordinates = stolenCarLocationService.getCoordinatesOfIcan(ICAN);
        coordinates.addListener((ListChangeListener<Coordinate>) c -> updateLiveTrackingMap(coordinates));
    }

    private void updateLiveTrackingMap(ObservableList<Coordinate> coordinates) {
        googleMap.clearMarkers();
        coordinates.forEach(c -> googleMap.addMarker(new GoogleMapMarker("", new LatLon(c.getLat(), c.getLng()), false, null)));
        getUI().push();
    }

    private void updateMapWithLocationHistory(String ICAN) throws InterruptedException {
        googleMap.clearMarkers();
        googleMap.removeAllComponents();

        if (this.lastLine != null) {
            googleMap.removePolyline(this.lastLine);
        }

        List<IcanCoordinateDTO> dtos = new ArrayList<>();
        stolenCarLocationService.getLocationHistory(ICAN, dtos);

        long time = System.currentTimeMillis();
        long end = time + 10000;

        //wait max 10 seconds
        Notification.show("Waiting for data from remote server", Notification.Type.ASSISTIVE_NOTIFICATION);
        while (dtos.isEmpty()) {
            if (System.currentTimeMillis() > end) {
                break;
            }
            Thread.sleep(100);
        }
        System.out.println("Time passed: " + (System.currentTimeMillis() - time));
        if (dtos.isEmpty()) {
            Notification.show("This ICAN was not found. Please do not try again.", Notification.Type.ERROR_MESSAGE);
            return;
        }

        googleMap.clearMarkers();
        System.out.println("size of dtos: " + dtos.size());

        List<LatLon> latlons = new ArrayList<>();
        dtos.forEach(d -> latlons.add(new LatLon(d.getLat(), d.getLng())));

        System.out.println("Size of latlons: " + latlons.size());

        GoogleMapPolyline line = new GoogleMapPolyline(latlons, "#ff0000", 0.8, 5);
        googleMap.addPolyline(line);
        this.lastLine = line;

        IcanCoordinateDTO lastDto = dtos.get(dtos.size() - 1);

        GoogleMapMarker lastMarker = new GoogleMapMarker(lastDto.getICAN(), new LatLon(lastDto.getLat(), lastDto.getLng()), false, null);
        googleMap.addMarker(lastMarker);
        getUI().push();
    }

    private VerticalLayout getTestMap() {
        VerticalLayout layout = new VerticalLayout();
        //layout.setSizeFull();
        GoogleMap testGoogleMap = new GoogleMap(this.apiKey, null, "english");
        testGoogleMap.setSizeFull();
        testGoogleMap.setHeight("700px");
        testGoogleMap.addMarker("DRAGGABLE: Paavo Nurmi Stadion", new LatLon(
                60.442423, 22.26044), true, "VAADIN/1377279006_stadium.png");
        testGoogleMap.addMarker("NOT DRAGGABLE: Iso-Heikkilä", new LatLon(
                60.450403, 22.230399), false, null);
        testGoogleMap.setMinZoom(4);
        testGoogleMap.setMaxZoom(16);
        layout.addComponent(testGoogleMap);

        return layout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        if (!securitySingleton.isLoggedIn(VaadinSession.getCurrent().getSession().getId())) {
            UI ui = UI.getCurrent();
            Navigator navigator = ui.getNavigator();
            navigator.navigateTo(LoginView.LOGIN_VIEW);
        }
        //initialized in init
    }
}
