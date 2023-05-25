package com.teamfour.iae;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ScriptInputPageController implements Initializable {

    @FXML
    MFXTextField inputScriptPath;

    @FXML
    MFXButton chooseButton;

    @FXML
    MFXButton continueButton;

    String[] scriptFolderAndFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        inputScriptPath.setEditable(false);

        chooseButton.setOnAction(event -> OnChoose());
        continueButton.setOnAction(event -> OnContinue());

    }

    private void OnChoose(){

        scriptFolderAndFile = Helpers.parentFolderChooser(chooseButton, "Choose Script File");
        inputScriptPath.setText(scriptFolderAndFile[0]+"\\"+scriptFolderAndFile[1]);

    }

    //todo scriptFolderAndFile[0] = seçilen dosyanın parent klasörünün adı
    //     scriptFolderAndFile[1] = seçilen dosyanın adı
    // dosya adı configuration'daki ile aynı olmazsa ve configuration ile aynı dilde olmazsa çalışmıyor

    private void OnContinue() {
        Configuration currentConfig = ProjectManager.getInstance().getCurrentProject().getConfiguration();
        Project currentProject = ProjectManager.getInstance().getCurrentProject();

        //Compile Code
        ProcessManager processManager = new ProcessManager();
        try {
            processManager.CompileFile(currentProject.getConfiguration(), scriptFolderAndFile[0]);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        //Run Exe
        try {
            processManager.RunExecutable(currentConfig, scriptFolderAndFile[0]);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        String testInputString;
        try {
            testInputString = new String(Files.readAllBytes(Paths.get(scriptFolderAndFile[0] + "\\" + Helpers.runtimeOutputLog)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ProjectManager.getInstance().getCurrentProject().setTestInput(testInputString);


        //Helpers.showAlert(Alert.AlertType.INFORMATION,"Information","Not implemented,yet. Please try manual input method.");
        Helpers.CloseStage(continueButton);

    }

}