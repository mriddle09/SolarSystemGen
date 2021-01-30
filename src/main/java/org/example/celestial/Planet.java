package org.example.celestial;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import org.example.Point;
import org.example.SimplexNoise;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Planet extends Sphere{
    Point[][] worldPoints;
    final int WIDTH;
    final int HEIGHT;
    BufferedImage map;
    PlanetType type;
    int seed;
    Random random = new Random();
    RotateTransition rote;
    int planetNum;
    String num;
    String fileName;

    public Planet(int pn) {
        super();
        planetNum = pn;
        num = Integer.toString(planetNum);
        fileName = "resources/planets/Planet" + num + ".png";
        WIDTH = 4500;
        HEIGHT = 4500;
        type = randWorldType();
        map = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_ARGB);
        this.worldInit();
        seed = random.nextInt();
        this.worldGen();

        Rotate r = new Rotate();
        r.setAngle(23);

    }

    public Planet(int pn, PlanetType t) {
        super();
        planetNum = pn;
        num = Integer.toString(planetNum);
        fileName = "resources/planets/Planet" + num + ".png";
        WIDTH = 4500;
        HEIGHT = 4500;
        type = t;
        map = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_ARGB);
        this.worldInit();
        seed = random.nextInt();
        this.worldGen();

    }

    PlanetType randWorldType() {
        int typeNum = random.nextInt(4);
        switch (typeNum) {
            case 0 : return PlanetType.GASGIANT;
            case 1: return PlanetType.UNINHABITABLE;
            case 2: return PlanetType.BARREN;
            case 3: return PlanetType.HABITABLE;
        }
        return PlanetType.BARREN;
    }

    void worldInit() {
        worldPoints = new Point[WIDTH][HEIGHT];
        for (int a = 0; a < WIDTH; a++) {
            for (int b = 0; b < HEIGHT; b++) {
                worldPoints[a][b] = new Point();
            }
        }

    }

    void worldGen() {
        switch (type) {
            case HABITABLE:
                this.generateSimplexNoise();
                this.colorHabitableMap();
                break;
            case GASGIANT:
                this.generateGasBands();
                this.colorGasMap();
                break;
            case BARREN:
                this.generateBarren();
                this.colorBarrenMap();
                break;
            case UNINHABITABLE:
                this.generateUninhabitable();
                this.colorUninhabitable();
                break;
        }

        rote = rotatePlanet();
        rote.play();
    }

    void generateSimplexNoise() {
        SimplexNoise noiseGen = new SimplexNoise();
        Random rand = new Random();
        double z = rand.nextDouble();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                double dx = ((double)j/(double)HEIGHT* (9));
                double dy = ((double)i/(double)WIDTH* (9));
                double nx = dx - 0.5;
                double ny = dy - 0.5;
                double noise = noiseGen.noise(nx, ny, z);
                //System.out.println(dx + " " + nx + " "+ dy + " " + ny + " " + noise);
                worldPoints[j][i].setElevation(noise);
            }
        }
    }

    void generateGasBands() {
        SimplexNoise gasNoise = new SimplexNoise();
        double s = seed;
        for (int i = 0; i < HEIGHT; i++) {
            double dx = ((double)i/(double)HEIGHT * 9);
            double nx = dx - 0.5;
            double gNoise = gasNoise.noise(nx, s);
            for(int j = 0; j < WIDTH; j++) {
                worldPoints[i][j].setElevation(gNoise);
            }
        }
    }

    void paintPlanet() {
        writeMap();
        PhongMaterial mat = new PhongMaterial();
        try {
            Image image = new Image(new FileInputStream(fileName));
            mat.setDiffuseMap(image);
        } catch (Exception e) {}
        this.setRadius(50);
        this.setTranslateX(800);
        this.setTranslateY(300);
        this.setTranslateZ(0);
        this.setMaterial(mat);
    }

    void colorHabitableMap() {

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (worldPoints[j][i].getElevation() > 0.1) {
                    map.setRGB(j,i, Color.green.getRGB());
                } else {
                    map.setRGB(j, i, Color.blue.getRGB());
                }
            }
        }

        paintPlanet();

    }

    void colorGasMap() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (worldPoints[j][i].getElevation() < -0.6) {
                    Color gas1 = new Color(235,243,246);
                    map.setRGB(i,j, gas1.getRGB());
                }
                if (worldPoints[j][i].getElevation() >= -0.6 && worldPoints[j][i].getElevation() < -0.2) {
                    Color gas2 = new Color(227,220,203);
                    map.setRGB(i,j, gas2.getRGB());
                }
                if (worldPoints[j][i].getElevation() >= -0.2 && worldPoints[j][i].getElevation() < 0.2) {
                    Color gas3 = new Color(216,202,157);
                    map.setRGB(i,j,gas3.getRGB());
                }
                if (worldPoints[j][i].getElevation() >= 0.2 && worldPoints[j][i].getElevation() < 0.6) {
                    Color gas4 = new Color(165,145,134);
                    map.setRGB(i,j,gas4.getRGB());
                }
                if (worldPoints[j][i].getElevation() >= 0.6) {
                    Color gas5 = new Color(201,144,57);
                    map.setRGB(i,j,gas5.getRGB());
                }

            }
        }

        paintPlanet();

    }

    void generateBarren() {
        Random randB = new Random();
        int craterNum = randB.nextInt(10) + 10;
        int[][] craters = new int[craterNum][3];

        for (int i = 0; i < craterNum; i++) {
            craters[i][0] = randB.nextInt(WIDTH);
            craters[i][1] = randB.nextInt(HEIGHT);
            craters[i][2] = randB.nextInt(405) + 45;
        }

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                worldPoints[j][i].setElevation(-1.0);
            }
        }

        for (int c = 0; c < craterNum; c++) {
            int raduis = craters[c][2];
            int x = craters[c][0];
            int y = craters[c][1];
            for (int w = x - raduis; w <= x + raduis; w++) {
                for (int h = y - raduis; h <= y + raduis; h++) {
                    if (h < 4500 && h >= 0 && w < 4500 && w >= 0) {
                        if ( Math.sqrt(((w - x) * (w - x)) + ((h - y) * (h - y))) <= raduis ) {
                            worldPoints[w][h].setElevation(1.0);
                        }
                    }
                }
            }
        }

    }

    void colorBarrenMap() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (worldPoints[i][j].getElevation() < 0) {
                    map.setRGB(j,i, Color.gray.getRGB());
                } else {
                    map.setRGB(j,i, Color.darkGray.getRGB());
                }

            }
        }

        paintPlanet();
    }

    void generateUninhabitable() {
        Random randU = new Random();
        int craterNum = randU.nextInt(600) + 100;
        int[][] craters = new int[craterNum][3];

        for (int i = 0; i < craterNum; i++) {
            craters[i][0] = randU.nextInt(WIDTH);
            craters[i][1] = randU.nextInt(HEIGHT);
            craters[i][2] = randU.nextInt(100) + 20;
        }

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                worldPoints[j][i].setElevation(-1.0);
            }
        }

        for (int c = 0; c < craterNum; c++) {
            int raduis = craters[c][2];
            int x = craters[c][0];
            int y = craters[c][1];
            for (int w = x - raduis; w <= x + raduis; w++) {
                for (int h = y - raduis; h <= y + raduis; h++) {
                    if (h < 4500 && h >= 0 && w < 4500 && w >= 0) {
                        if ( Math.sqrt(((w - x) * (w - x)) + ((h - y) * (h - y))) <= raduis ) {
                            worldPoints[w][h].setElevation(1.0);
                        }
                    }
                }
            }
        }

    }

    void colorUninhabitable() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (worldPoints[j][i].getElevation() < 0) {
                    map.setRGB(j,i, Color.orange.getRGB());
                } else {
                    map.setRGB(j,i, Color.YELLOW.getRGB());
                }

            }
        }

        paintPlanet();
    }

    public void writeMap() {
        try {
            File output = new File(this.fileName);
            ImageIO.write(map, "png", output);
        } catch (IOException e) {

        }
    }

    public void printPlanetType() {
        String out = "";
        switch (type) {
            case UNINHABITABLE:
                out = "Uninhabitable";
                break;
            case BARREN:
                out = "Barren";
                break;
            case GASGIANT:
                out = "Gas Giant";
                break;
            case HABITABLE:
                out = "Habitable";
                break;
        }
        System.out.println(out);
    }

    public RotateTransition rotatePlanet() {

        RotateTransition rotate = new RotateTransition(Duration.seconds(8), this);
        Point3D point = new Point3D(100, 500, 100);
        rotate.setAxis(point);
        rotate.setFromAngle(360);
        rotate.setToAngle(0);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        return rotate;

    }

    public void startRotate() {
        rote.play();
    }

    public void movePlanet() {

    }

}
