package com.dreamteam.police.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

/**
 * Created by Loci on 15-5-2017.
 */
@SpringView(name = ReportCarView.REPORT_CAR_VIEW)
@ViewScope
public class ReportCarView extends VerticalLayout implements View {
    public static final String REPORT_CAR_VIEW = "report_car";

    @PostConstruct
    void init() {

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        //initialized in init method
    }
}
