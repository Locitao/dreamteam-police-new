package com.dreamteam.police.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

/**
 * Created by Loci on 30-4-2017.
 */
@SpringView(name = SearchCarTrackerView.SEARCH_CAR_VIEW)
public class SearchCarTrackerView extends VerticalLayout implements View {

    public static final String SEARCH_CAR_VIEW = "SearchCarTracker";

    @PostConstruct
    void init() {
        TextArea carTrackerTextBox = new TextArea("Car tracker:");
        addComponent(carTrackerTextBox);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        //constructed in init method
    }
}
