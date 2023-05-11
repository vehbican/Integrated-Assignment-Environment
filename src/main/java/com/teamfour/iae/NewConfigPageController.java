package com.teamfour.iae;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NewConfigPageController implements Initializable {

    @FXML
    MFXTextField configNameInput;

    @FXML
    MFXTextField compilerParametersInput;

    @FXML
    MFXTextField mainFileInput;

    @FXML
    MFXTextField exeNameInput;

    @FXML
    MFXTextField compilerPathInput;

    @FXML
    MFXButton chooseCompilerPathButton;

    @FXML
    MFXButton createButton;

    @FXML
    MFXButton importButton;

    DataManager manager = new DataManager();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createButton.setOnAction(event -> OnExportButton());
        chooseCompilerPathButton.setOnAction(event -> OnChooseCompilerButton());
        importButton.setOnAction(event -> OnImportButton());
    }

    private void OnImportButton() {
        Configuration deserialized;
        try {
           deserialized = (Configuration) manager.DeserializeObject(fileChooser());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        configNameInput.setText(deserialized.getName());
        compilerPathInput.setText(deserialized.getCompilerPath());
        exeNameInput.setText(deserialized.getExecutableName());
        mainFileInput.setText(deserialized.getMainFileName());
        String joinedString = String.join(" ", deserialized.getCompilerParameters());
        compilerParametersInput.setText(joinedString);
    }

    private void OnChooseCompilerButton() {
            String filePath = fileChooser();
            compilerPathInput.setText(filePath);
    }

    private void OnExportButton() {
        Configuration config = new Configuration(); //todo make it singleton for general usage
        config.setName(configNameInput.getText());
        config.setCompilerPath(compilerPathInput.getText());
        config.setExecutableName(exeNameInput.getText());
        config.setMainFileName(mainFileInput.getText());
        config.getCompilerParameters().addAll(List.of(compilerParametersInput.getText().trim().split(" ")));

        String path = "configs/"+configNameInput.getText()+".txt";

        try {
            manager.SerializeObject(config,path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) createButton.getScene().getWindow();
        stage.close();
    }

    private String fileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Compiler");

        File initialDirectory = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(initialDirectory);

        Stage stage = (Stage) chooseCompilerPathButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        return selectedFile.getAbsolutePath();
    }
}