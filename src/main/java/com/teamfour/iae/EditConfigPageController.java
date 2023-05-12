package com.teamfour.iae;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditConfigPageController implements Initializable {

    @FXML
    MFXComboBox<Configuration> configurationsComboBox;

    @FXML
    MFXTextField compilerParametersInput;

    @FXML
    MFXTextField mainFileInput;

    @FXML
    MFXTextField exeNameInput;

    @FXML
    MFXTextField configNameInput;

    @FXML
    MFXTextField compilerPathInput;

    @FXML
    MFXButton chooseCompilerPathButton;

    @FXML
    MFXButton editButton;

    @FXML
    MFXButton deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        configurationsComboBox.setOnAction(event ->  handleConfigurationSelected());
        chooseCompilerPathButton.setOnAction(event -> OnChooseCompilerButton());
        editButton.setOnAction(event ->  OnEditButton());
        deleteButton.setOnAction(event -> OnDeleteButton());

        configurationsComboBox.getItems().addAll(FXCollections.observableArrayList(ProjectManager.getInstance().getImportedConfigurations()));


    }

    private void OnDeleteButton() {
        Configuration selectedConfig = configurationsComboBox.getValue();
        if (selectedConfig != null) {
            String configFilePath = "configs/" + selectedConfig + ".txt";
            File configFile = new File(configFilePath);
            if (configFile.exists()) {
                boolean isDeleted = configFile.delete();
                if (isDeleted) {
                    configurationsComboBox.getItems().remove(selectedConfig);
                    configurationsComboBox.getSelectionModel().clearSelection();
                    compilerPathInput.clear();
                    compilerParametersInput.clear();
                    mainFileInput.clear();
                    exeNameInput.clear();
                    configNameInput.clear();
                    ProjectManager.getInstance().getImportedConfigurations().remove(selectedConfig);
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

        Configuration selectedConfig = configurationsComboBox.getValue();

        String s = selectedConfig.getName();

        selectedConfig.setCompilerPath(compilerPathInput.getText());
        selectedConfig.setMainFileName(mainFileInput.getText());
        selectedConfig.setExecutableName(exeNameInput.getText());
        selectedConfig.setName(configNameInput.getText());
        selectedConfig.setCompilerParameters(List.of(compilerParametersInput.getText().trim().split(" ")));

        String path = "configs/"+selectedConfig.getName()+".txt";

        boolean isRenamed = new File("configs/"+s+".txt").renameTo(new File(path));

        if (isRenamed){

            try {
                DataManager.SerializeObject(selectedConfig,path);
                compilerPathInput.clear();
                compilerParametersInput.clear();
                mainFileInput.clear();
                exeNameInput.clear();
                configNameInput.clear();
                showAlert(Alert.AlertType.INFORMATION, "Configuration Edited", "Configuration file has been successfully edited.");
                closeWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    private void OnChooseCompilerButton() {
        String filePath = fileChooser();
        compilerPathInput.setText(filePath);
    }


    private void handleConfigurationSelected() {
        Configuration selectedConfig = configurationsComboBox.getValue();

        if (selectedConfig != null) {

            mainFileInput.setText(selectedConfig.getMainFileName());
            exeNameInput.setText(selectedConfig.getExecutableName());
            configNameInput.setText(selectedConfig.getName());
            compilerPathInput.setText(selectedConfig.getCompilerPath());
            compilerParametersInput.setText(String.join(" ", selectedConfig.getCompilerParameters()));

        }else {
            showAlert(Alert.AlertType.ERROR, "Configuration Not Found", "Configuration file not found for the selected compiler.");
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