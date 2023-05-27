package com.teamfour.iae;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewProjectPageController implements Initializable {

    @FXML
    MFXTextField projectNameInput;

    @FXML
    MFXComboBox<Configuration> configurationComboBox;

    @FXML
    MFXButton createConfigButton;

    @FXML
    MFXButton importConfigButton;

    @FXML
    MFXTextField zipFilesDirectoryInput;

    @FXML
    MFXButton chooseZipFilesDirectoryButton;

    @FXML
    MFXComboBox<Project.InputMethods> inputMethodComboBox;

    @FXML
    MFXTextField expectedOutputFileInput;

    @FXML
    MFXButton chooseExpectedOutputFileButton;

    @FXML
    MFXButton createProjectButton;

    public HomepageController homepageController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        configurationComboBox.setOnMouseEntered(event -> configurationComboBox.getItems().setAll(FXCollections.observableArrayList(ProjectManager.getInstance().getImportedConfigurations())));

        configurationComboBox.getItems().addAll(FXCollections.observableArrayList(ProjectManager.getInstance().getImportedConfigurations()));


        ObservableList<Project.InputMethods> inputMethods = FXCollections.observableArrayList(Project.InputMethods.MANUAL,Project.InputMethods.VIA_EXECUTABLE);
        inputMethodComboBox.getItems().addAll(inputMethods);

        createConfigButton.setOnAction(event -> OnCreateConfig());
        importConfigButton.setOnAction(event -> OnImportConfig());
        chooseZipFilesDirectoryButton.setOnAction(event -> OnChooseZipDirectory());
        chooseExpectedOutputFileButton.setOnAction(event -> OnChooseExpectedOutputFile());
        createProjectButton.setOnAction(event -> OnCreateProject());

        zipFilesDirectoryInput.setEditable(false);
        expectedOutputFileInput.setEditable(false);



    }

    private void OnCreateProject(){

        Project project = new Project();
        project.setName(projectNameInput.getText().trim());

        if (configurationComboBox.getValue() != null){

            project.setConfiguration(configurationComboBox.getValue());

        }else {

            Helpers.showAlert(Alert.AlertType.WARNING,"Select Configuration","Please set the project configuration.");
            return;
        }

        project.setZippedSubmissionsDirectory(zipFilesDirectoryInput.getText().trim());

        project.setExtractedSubmissionsDirectory(Helpers.submissionsDir);

        project.setExpectedOutputFilePath(expectedOutputFileInput.getText().trim());

        project.setInputMethod(inputMethodComboBox.getValue());

        ZipExtractor.extractZipFiles(project.getZippedSubmissionsDirectory(),Helpers.submissionsDir);

        ProjectManager.getInstance().setCurrentProject(project);

        homepageController.projectInfo.setText(project.showProjectInfo());
        homepageController.configInfo.setText(project.getConfiguration().ConfigInfo());

        Helpers.showAlert(Alert.AlertType.CONFIRMATION,"Project Created","This project is created successfully.");

        closeWindow();

    }

    private void OnCreateConfig(){

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("new-config-page.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 400, 500);
            Stage stage = new Stage();
            stage.setTitle("New Configuration");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    private void OnImportConfig(){

        Configuration deserialized;
        try {
            deserialized = (Configuration) DataManager.DeserializeObject(Helpers.fileChooser(importConfigButton,"Select Configuration"));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (deserialized != null){

            ProjectManager.getInstance().setCurrentConfiguration(deserialized);
            ProjectManager.getInstance().getImportedConfigurations().add(deserialized);
            Helpers.showAlert(Alert.AlertType.INFORMATION,"Configuration Imported",deserialized.ConfigInfo());

        }else {

            Helpers.showAlert(Alert.AlertType.ERROR,"Import Error","Import failed.");

        }


    }

    private void OnChooseZipDirectory(){

        zipFilesDirectoryInput.setText(Helpers.directoryChooser(chooseZipFilesDirectoryButton,"Select Zip Files Directory"));

    }

    private void OnChooseExpectedOutputFile(){

        expectedOutputFileInput.setText(Helpers.fileChooser(chooseExpectedOutputFileButton,"Select Expected Output File"));


    }

    private void closeWindow() {
        Stage stage = (Stage) createProjectButton.getScene().getWindow();
        stage.close();
    }

}
