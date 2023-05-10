package com.teamfour.iae;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("homepage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("Integrated Assignment Environment");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.initStyle(StageStyle.DECORATED);
        stage.show();




    }

    public static void main(String[] args) {
        launch();
    }
}