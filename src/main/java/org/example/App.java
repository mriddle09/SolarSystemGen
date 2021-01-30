package org.example;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.celestial.Planet;
import org.example.celestial.PlanetType;
import org.example.celestial.SolarSystem;
import org.example.celestial.Star;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * JavaFX App
 */
public class App extends Application {

    SolarSystem mySol = new SolarSystem();
    Scene scene = new Scene(new Group(mySol), 1500, 600, Color.BLACK);

    @Override
    public void start(Stage stage) throws FileNotFoundException {

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}