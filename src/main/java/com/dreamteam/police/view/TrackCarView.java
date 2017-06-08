package com.dreamteam.police.view;

import com.dreamteam.police.jms.IcanCoordinateDTO;
import com.dreamteam.police.model.Coordinate;
import com.dreamteam.police.service.StolenCarLocationService;
import com.google.gson.Gson;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
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

    private final String apiKey = "obviouslyfake";

    private GoogleMap googleMap;

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
            }
        });
        layout.addComponent(trackCar);

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
        coordinates.addListener((ListChangeListener<Coordinate>) c -> updateMap(coordinates));
    }

    private void updateMap(ObservableList<Coordinate> coordinates) {
        googleMap.clearMarkers();
        coordinates.forEach(c -> googleMap.addMarker(new GoogleMapMarker("", new LatLon(c.getLat(), c.getLng()), false, null)));
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
        //initialized in init
    }
}
