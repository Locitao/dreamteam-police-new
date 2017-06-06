package com.dreamteam.police.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

/**
 * Created by Loci on 6-6-2017.
 */
@SpringView(name = TrackCarView.TRACK_CAR)
@ViewScope
public class TrackCarView extends VerticalLayout implements View {
    static final String TRACK_CAR="track_car";

    private final String apiKey = "obviouslyfake";

    @PostConstruct
    void init() {
        VerticalLayout root = new VerticalLayout();
        //root.setSizeFull();

        root.addComponent(getMap());

        addComponent(root);
    }

    private VerticalLayout getMap() {
        VerticalLayout layout = new VerticalLayout();
        //layout.setSizeFull();
        GoogleMap googleMap = new GoogleMap(this.apiKey, null, "english");
        googleMap.setSizeFull();
        googleMap.setHeight("700px");
        googleMap.addMarker("DRAGGABLE: Paavo Nurmi Stadion", new LatLon(
                60.442423, 22.26044), true, "VAADIN/1377279006_stadium.png");
        googleMap.addMarker("NOT DRAGGABLE: Iso-Heikkilä", new LatLon(
                60.450403, 22.230399), false, null);
        googleMap.setMinZoom(4);
        googleMap.setMaxZoom(16);
        layout.addComponent(googleMap);

        return layout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        //initialized in init
    }
}
