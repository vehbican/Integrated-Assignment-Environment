package com.teamfour.iae;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Helpers {

    public static void showAlert(Alert.AlertType alertType,String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static String fileChooser(Node node,String title){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);

        File initialDirectory = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(initialDirectory);

        Stage stage = (Stage) node.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null){

            return selectedFile.getAbsolutePath();

        }

        return "You did not choose a valid path";

    }

    public static String directoryChooser(Node node,String title){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(title);

        File initialDirectory = new File(System.getProperty("user.home"));
        directoryChooser.setInitialDirectory(initialDirectory);

        Stage stage = (Stage) node.getScene().getWindow();
        File selectedFile = directoryChooser.showDialog(stage);

        if (selectedFile != null){

            return selectedFile.getAbsolutePath();

        }

        return "You did not choose a valid path";

    }

    public static String configsDir = "configs";
    public static String submissionsDir = "submissions";



}
