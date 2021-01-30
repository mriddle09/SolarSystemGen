package org.example.celestial;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.PointLight;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.FileInputStream;

/** Extends the JavaFX class Sphere in order to
 *  create an object representing the Sun using an image file.
 */

public class Star extends Sphere {

    PointLight light = new PointLight();
    PhongMaterial mat = new PhongMaterial();



    public Star() {
        super();
        this.setRadius(300);
        this.setLocation(300,300,0);
        light.setTranslateX(this.getTranslateX());
        light.setTranslateY(this.getTranslateY());
        light.setTranslateZ(this.getTranslateZ());

        try {
            Image image = new Image(new FileInputStream("resources/sunmap.jpg"));
            mat.setSelfIlluminationMap(image);
            mat.setSpecularColor(Color.WHITE);
        } catch (Exception e) {}

        Glow glow = new Glow();
        glow.setLevel(10);
        this.setEffect(glow);
        this.setMaterial(mat);

        rotateStar().play();


    }

    void setLocation(double x, double y, double z) {
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setTranslateZ(z);
    }

    public RotateTransition rotateStar() {
        RotateTransition rotate = new RotateTransition(Duration.seconds(40), this);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setFromAngle(360);
        rotate.setToAngle(0);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        return rotate;
    }

}
