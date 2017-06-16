package com.dreamteam.police.view;

import com.dreamteam.police.jms.Sender;
import com.dreamteam.police.jms.StolenDto;
import com.dreamteam.police.jms.StolenJmsDto;
import com.dreamteam.police.security.SecuritySingleton;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.Instant;

/**
 * Created by Loci on 30-4-2017.
 */
@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "DEFAULT";

    @Autowired
    Sender jmsSender;

    @Autowired
    private SecuritySingleton securitySingleton;

    @PostConstruct
    void init() {
        addComponent(new Label("Please click on one of the buttons above for useful stuff."));
        addComponent(new Label("Under search car trackers you can view car trackers based on their ICAN."));
        addComponent(new Label("Under ownership view you can inspect currently known ownerships of cars."));
        addComponent(new Label("Under car reporting you can report cars as stolen, found or other."));
        addComponent(new Label("Under track car you can live track a car by ICAN, or look up the location history of a car."));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (!securitySingleton.isLoggedIn(VaadinSession.getCurrent().getSession().getId())) {
            UI ui = UI.getCurrent();
            Navigator navigator = ui.getNavigator();
            navigator.navigateTo(LoginView.LOGIN_VIEW);
        }
        // This view is constructed in the init() method()
    }
}
