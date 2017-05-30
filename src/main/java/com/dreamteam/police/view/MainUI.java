package com.dreamteam.police.view;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Loci on 30-4-2017.
 */
@Theme("valo")
@SpringUI
@SpringViewDisplay
public class MainUI extends UI implements ViewDisplay {

    private Panel springViewDisplay;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        setContent(root);

        root.addComponent(createCssLayout());

        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();

        root.addComponent(springViewDisplay);
        root.setExpandRatio(springViewDisplay, 1f);
    }

    @Override
    public void showView(View view) {
        springViewDisplay.setContent((Component) view);
    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    private CssLayout createCssLayout() {
        //TODO: perhaps replace with an actual MenuBar object?
        final CssLayout navigationBar = new CssLayout();
        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        navigationBar.addComponent(createNavigationButton("Home", DefaultView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Search car trackers", SearchCarTrackerView.SEARCH_CAR_VIEW));
        navigationBar.addComponent(createNavigationButton("Ownership view", NewOwnershipView.NEW_OWNERSHIP_VIEW));
        navigationBar.addComponent(createNavigationButton("Car reporting", ReportCarView.REPORT_CAR_VIEW));
        return navigationBar;
    }
}
