package org.example;

import javafx.application.Preloader;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FirstPreloader extends Preloader {
    Image image;
    ImageView im;
    Stage stage;
    AnchorPane myPane;
    Scene help;

    void sceneInit() {
            try {
                im = new ImageView(new Image(new FileInputStream("SolarSplash.png")));
            } catch (FileNotFoundException e) {

            }
            myPane = new AnchorPane(im);
            //myPane.toFront();
            //myPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
    }

    private void createPreloaderScene() throws FileNotFoundException {
        sceneInit();
        Scene myScene = new Scene(myPane);
        myScene.setFill(Color.TRANSPARENT);
        help = myScene;
    }

    public void start(Stage stage) throws Exception {

            createPreloaderScene();
            this.stage = stage;
            //stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(help);

            stage.show();
            System.out.println("Show me");


    }

    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        //bar.setProgress(pn.getProgress());
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }
}
