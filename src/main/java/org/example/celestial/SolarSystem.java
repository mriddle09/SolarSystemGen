package org.example.celestial;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Random;

/** The SolarSystem program extends the JavaFX class Group
 *  to hold objects representing the Sun and randomly generated planets.
 */

public class SolarSystem extends Group {

    Star sun = new Star();
    Random rand = new Random();
    ArrayList<PlanetPane> planets = new ArrayList<PlanetPane>();

    public SolarSystem() {
        super();
        this.getChildren().add(sun);
        createPlanets();

    }

    void createPlanets() {
        int planetNum = 4; //rand.nextInt(5) + 1;
        for (int i = 0; i < planetNum; i++) {
            planets.add(new PlanetPane(i + 1));
            planets.get(i).setTranslateX((planets.get(0).getTranslateX() + (i * 200)));
            this.getChildren().add(planets.get(i));
        }
    }
}
