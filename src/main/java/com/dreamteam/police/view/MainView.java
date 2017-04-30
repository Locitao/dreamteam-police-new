package com.dreamteam.police.view;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Loci on 30-4-2017.
 */
@Theme("valo")
@SpringUI
public class MainView extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout root = new VerticalLayout();

        Label label = new Label("Test!");

        root.addComponent(label);

        setContent(root);
    }
}
