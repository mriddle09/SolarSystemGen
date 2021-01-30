package org.example;

import javafx.application.Application;

public class Launcher {
    public static void main (String[] args) {
        //System.setProperty("javafx.preloader", FirstPreloader.class.getCanonicalName());
        Application.launch(App.class, args);
    }
}
