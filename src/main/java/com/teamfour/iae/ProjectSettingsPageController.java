package com.teamfour.iae;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectSettingsPageController implements Initializable {
    public MFXTextField projectNameInput;
    public MFXComboBox<Configuration> configurationComboBox;
    public MFXTextField zipFilesDirectoryInput;
    public MFXButton chooseZipFilesDirectoryButton;
    public MFXComboBox<Project.InputMethods> inputMethodComboBox;
    public MFXTextField expectedOutputFileInput;
    public MFXButton chooseExpectedOutputFileButton;
    public MFXButton saveSettingsButton;

    public HomepageController homepageController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        configurationComboBox.setOnMouseEntered(event -> configurationComboBox.getItems().setAll(FXCollections.observableArrayList(ProjectManager.getInstance().getImportedConfigurations())));

        configurationComboBox.getItems().addAll(FXCollections.observableArrayList(ProjectManager.getInstance().getImportedConfigurations()));


        ObservableList<Project.InputMethods> inputMethods = FXCollections.observableArrayList(Project.InputMethods.MANUAL,Project.InputMethods.VIA_EXECUTABLE);
        inputMethodComboBox.getItems().addAll(inputMethods);

        chooseZipFilesDirectoryButton.setOnAction(event -> OnChooseZipDirectory());
        chooseExpectedOutputFileButton.setOnAction(event -> OnChooseExpectedOutputFile());
        saveSettingsButton.setOnAction(event -> OnSaveProjectSettings());

        Project current = ProjectManager.getInstance().getCurrentProject();

        projectNameInput.setText(current.getName());
        configurationComboBox.selectItem(current.getConfiguration());
        zipFilesDirectoryInput.setText(current.getZippedSubmissionsDirectory());
        inputMethodComboBox.selectItem(current.getInputMethod());
        expectedOutputFileInput.setText(current.getExpectedOutputFilePath());

        zipFilesDirectoryInput.setEditable(false);
        expectedOutputFileInput.setEditable(false);


    }

    private void OnSaveProjectSettings(){


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
        ProjectManager.getInstance().setCurrentConfiguration(project.getConfiguration());

        homepageController.projectInfo.setText(project.showProjectInfo());
        homepageController.configInfo.setText(project.getConfiguration().ConfigInfo());

        Helpers.showAlert(Alert.AlertType.CONFIRMATION,"Project Settings Saved","This project is updated successfully.");

        Helpers.CloseStage(saveSettingsButton);

    }

    private void OnChooseZipDirectory(){

        zipFilesDirectoryInput.setText(Helpers.directoryChooser(chooseZipFilesDirectoryButton,"Select Zip Files Directory"));

    }

    private void OnChooseExpectedOutputFile(){

        expectedOutputFileInput.setText(Helpers.fileChooser(chooseExpectedOutputFileButton,"Select Expected Output File"));

    }



}
