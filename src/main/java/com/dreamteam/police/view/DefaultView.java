package com.dreamteam.police.view;

import com.dreamteam.police.jms.Sender;
import com.dreamteam.police.jms.StolenDto;
import com.dreamteam.police.jms.StolenJmsDto;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.Instant;

/**
 * Created by Loci on 30-4-2017.
 */
@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";

    @Autowired
    Sender jmsSender;

    @PostConstruct
    void init() {
        addComponent(new Label("Please click on one of the buttons above for useful stuff."));
//        Button test = new Button("send test message");
//        test.addClickListener(e -> {
//            StolenJmsDto stolenDto = new StolenJmsDto("asdf", "1234", Instant.now().getEpochSecond(), true);
//            jmsSender.sendMessage(stolenDto);
//        });
//        addComponent(test);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }
}
