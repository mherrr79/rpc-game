package com.nobel.game.views.game;

import com.nobel.game.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("Welcome to Rock Paper Scissors game")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class GameIntroView extends VerticalLayout {

    public GameIntroView() {
        setSpacing(false);

        Image img = new Image("images/rpc-icon.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        H2 header = new H2("Welcome to RPS. The game has to modes EASY and DIFFICULT");
        header.addClassNames(LumoUtility.Margin.Top.XLARGE, LumoUtility.Margin.Bottom.MEDIUM);
        add(header);
        add(new Paragraph("EASY mode simply remembers your past moves and the computer plays its move to counter your most frequent moves!"));
        add(new Paragraph("DIFFICULT mode uses Markov Chain like structure (transition matrix) and remembers chains of your past moves!"));

        Button button = new Button("PLAY NOW");
        button.addClickListener( e -> UI.getCurrent().navigate(GameView.class));
        add(button);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
