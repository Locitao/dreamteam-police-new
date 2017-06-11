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
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
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

        addComponent(new Label(securitySingleton.isLoggedIn(VaadinSession.getCurrent().getSession().getId()) ? "logged in" : "fucked up"));
//        Button test = new Button("send test message");
//        test.addClickListener(e -> {
//            StolenJmsDto stolenDto = new StolenJmsDto("asdf", "1234", Instant.now().getEpochSecond(), true);
//            jmsSender.sendMessage(stolenDto);
//        });
//        addComponent(test);
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
