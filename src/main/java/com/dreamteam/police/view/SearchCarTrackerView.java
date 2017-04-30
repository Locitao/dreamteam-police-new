package com.dreamteam.police.view;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Loci on 30-4-2017.
 */
@SpringView(name = SearchCarTrackerView.SEARCH_CAR_VIEW)
@ViewScope
public class SearchCarTrackerView extends VerticalLayout implements View {

    static final String SEARCH_CAR_VIEW = "SearchCarTracker";

    private List<String> strings;
    private ListDataProvider<String> data;

    @PostConstruct
    void init() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        TextField carTrackerTextBox = new TextField("Car tracker:");
        verticalLayout.addComponent(carTrackerTextBox);

        Button button = new Button("Add to grid");
        button.setStyleName(ValoTheme.BUTTON_PRIMARY);
        button.addClickListener(e -> {
            String temp = carTrackerTextBox.getValue();
            strings.add(temp);
            data.refreshAll();
        });
        verticalLayout.addComponent(button);

        layout.addComponent(verticalLayout);

        strings = new ArrayList<>();
        strings.add("one");
        strings.add("two");
        data = DataProvider.ofCollection(strings);

        Grid<String> grid = new Grid<>();
        grid.setDataProvider(data);
        grid.addColumn(String::toUpperCase).setCaption("Ta-da");
        layout.addComponent(grid);
        layout.setComponentAlignment(grid, Alignment.TOP_RIGHT);



        addComponent(layout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        //constructed in init method
    }
}
