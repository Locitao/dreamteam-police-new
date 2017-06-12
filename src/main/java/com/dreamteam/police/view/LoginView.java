package com.dreamteam.police.view;

import com.dreamteam.police.security.SecuritySingleton;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by loci on 11-6-17.
 */
@SpringView(name = LoginView.LOGIN_VIEW)
@ViewScope
public class LoginView extends VerticalLayout implements View {
    public static final String LOGIN_VIEW = "";

    @Autowired
    private SecuritySingleton securitySingleton;

    @PostConstruct
    void init() {
        VerticalLayout root = new VerticalLayout();

        TextField username = new TextField("username");
        PasswordField password = new PasswordField("password");
        root.addComponent(username);
        root.addComponent(password);

        Button loginButton = new Button("login");
        loginButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        loginButton.addClickListener(e -> {
            String sessionId = VaadinSession.getCurrent().getSession().getId();

            boolean success = securitySingleton.login(username.getValue(), password.getValue(), sessionId);

           username.setValue("");
           password.setValue("");

           if (success) {
               getUI().getNavigator().navigateTo(DefaultView.VIEW_NAME);
           } else {
               Notification.show("Wrong username or password", Notification.Type.ERROR_MESSAGE);
           }
        });
        root.addComponent(loginButton);

        addComponent(root);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
