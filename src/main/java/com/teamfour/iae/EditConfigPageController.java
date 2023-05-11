package com.teamfour.iae;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditConfigPageController implements Initializable {

    @FXML
    MFXComboBox<String> compilerName;
    @FXML
    MFXTextField compilerPathInput;

    @FXML
    MFXTextField compilerParametersInput;

    @FXML
    MFXButton chooseCompilerPathButton;

    @FXML
    MFXButton editButton;

    @FXML
    MFXButton deleteButton;

    DataManager manager = new DataManager();

    Configuration config;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateCompilerNames();
        compilerName.setOnAction(event ->  handleCompilerNameSelected());
        chooseCompilerPathButton.setOnAction(event -> OnChooseCompilerButton());
        editButton.setOnAction(event ->  OnEditButton());
        deleteButton.setOnAction(event -> OnDeleteButton());
    }

    private void OnDeleteButton() {
        String selectedCompiler = compilerName.getValue();
        if (selectedCompiler != null) {
            String configFilePath = "configs/" + selectedCompiler + ".txt";
            File configFile = new File(configFilePath);
            if (configFile.exists()) {
                boolean isDeleted = configFile.delete();
                if (isDeleted) {
                    compilerName.getItems().remove(selectedCompiler);
                    compilerName.getSelectionModel().clearSelection();
                    compilerPathInput.clear();
                    compilerParametersInput.clear();
                    showAlert(Alert.AlertType.INFORMATION, "Configuration Deleted", "Configuration file has been successfully deleted.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Deletion Error", "Failed to delete the configuration file.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "File Not Found", "Configuration file not found for the selected compiler.");
            }
        }
    }

    private void OnEditButton() {

        config.setCompilerPath(compilerPathInput.getText());

        config.setCompilerParameters(List.of(compilerParametersInput.getText().trim().split(" ")));

        String path = "configs/"+config.getName()+".txt";

        try {
            manager.SerializeObject(config,path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        closeWindow();
    }

    private void OnChooseCompilerButton() {
        String filePath = fileChooser();
        compilerPathInput.setText(filePath);
    }

    private void populateCompilerNames() {
        File configsFolder = new File("configs");
        File[] configFiles = configsFolder.listFiles();

        if (configFiles != null) {
            List<String> compilerNames = new ArrayList<>();

            for (File configFile : configFiles) {
                if (configFile.isFile()) {
                    String configFilePath = configFile.getAbsolutePath();
                    Configuration config;
                    try {
                        config = (Configuration) manager.DeserializeObject(configFilePath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    if (config != null) {
                        String compilerName = config.getName();
                        if (compilerName != null && !compilerNames.contains(compilerName)) {
                            compilerNames.add(compilerName);
                        }
                    }
                }
            }

            compilerName.getItems().addAll(compilerNames);
        }
    }

    private void handleCompilerNameSelected() {
        String selectedCompiler = compilerName.getValue();

        if (selectedCompiler != null) {
            String configFilePath = "configs/" + selectedCompiler + ".txt";
            try {
                config = (Configuration) manager.DeserializeObject(configFilePath);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (config != null) {
                compilerPathInput.setText(config.getCompilerPath());
                compilerParametersInput.setText(String.join(" ", config.getCompilerParameters()));
            } else {
                showAlert(Alert.AlertType.ERROR, "Configuration Not Found", "Configuration file not found for the selected compiler.");
            }
        }
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) editButton.getScene().getWindow();
        stage.close();
    }

}