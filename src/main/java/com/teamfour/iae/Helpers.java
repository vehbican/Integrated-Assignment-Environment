package com.teamfour.iae;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

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

    public static void CloseStage(Node node){

        ((Stage)node.getScene().getWindow()).close();

    }

    public static boolean AreEqual(String file1, String file2) throws IOException {

        BufferedReader reader1 = new BufferedReader(new FileReader(file1));
        BufferedReader reader2 = new BufferedReader(new FileReader(file2));
        String line1 = reader1.readLine();
        String line2 = reader2.readLine();

        boolean areEqual = true;
        while (line1 != null || line2 != null){

            if(line1 == null || line2 == null){
                areEqual = false;
                break;
            } else if(! line1.equalsIgnoreCase(line2)) {
                areEqual = false;
                break;
            }

            line1 = reader1.readLine();
            line2 = reader2.readLine();

        }

        reader1.close();
        reader2.close();

        return areEqual;
    }


    public static String ReadFileIntoString(String path) throws IOException {

       return Files.readString(Path.of(path));

    }


    public static String configsDir = "configs";
    public static String submissionsDir = "submissions";

    public static String runtimeOutputLog = "runtimeOutput.txt";
    public static String runtimeErrorLog = "runtimeError.txt";
    public static String compileTimeOutputLog = "compileTimeOutput.txt";
    public static String compileTimeErrorLog = "compileTimeError.txt";


}
