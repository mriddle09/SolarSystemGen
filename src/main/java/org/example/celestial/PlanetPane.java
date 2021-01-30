package org.example.celestial;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Random;

public class PlanetPane extends Group {

    String[] names = {"Apollo", "Aurora", "Bacchus", "Artemis", "Bellona", "Caelus", "Cupid", "Cybele", "Diana", "Faunus", "Fortuna", "Janus", "Juno", "Minerva", "Nyx", "Prospenia", "Pompona", "Spes", "Terra", "Veritas", "Vulcan"};
    Planet myPlanet;
    Button myButton;
    VBox labels;
    Label planetName;
    Label planetType;
    VBox info;
    Random rand = new Random();

    PlanetPane(int planetNum) {
        info = new VBox();
        labels = new VBox();
        myPlanet = new Planet(planetNum);
        myButton = new Button();
        myButton.setTranslateY(myPlanet.getTranslateY() - 39);
        myButton.setTranslateX(myPlanet.getTranslateX() - 42);
        myButton.setAlignment(Pos.CENTER);
        myButton.setPrefSize(80,80);
        myButton.setStyle("-fx-background-color: transparent;");

        myButton.setOnAction( t -> {
            if (info.isVisible()) {
                info.setVisible(false);
            } else {
                info.setVisible(true);
            }
        });


        Label gravity = new Label("Gravity: 0." + rand.nextInt(100) + "G");
        Label raduis = new Label("Raduis: " + (rand.nextInt(10000) + 5000) + " km");
        Label atmosphere = new Label("Pressure: " + rand.nextInt(1000) + "mbar");
        info.getChildren().addAll(gravity, raduis, atmosphere);
        info.setTranslateY(myPlanet.getTranslateY() - 110);
        info.setTranslateX(myPlanet.getTranslateX() - 60);
        info.setAlignment(Pos.CENTER);
        info.setVisible(false);

        planetName = new Label(names[rand.nextInt(names.length - 1)]);
        planetName.setAlignment(Pos.CENTER);
        planetType = new Label(myPlanet.type.typeToString());
        planetType.setAlignment(Pos.CENTER);

        labels.getChildren().addAll(planetName, planetType);
        labels.setTranslateX(myPlanet.getTranslateX() - 30);
        labels.setTranslateY(myPlanet.getTranslateY() + 70);
        labels.setAlignment(Pos.CENTER);

        this.getChildren().addAll(myPlanet, myButton, info, labels);
    }
}
