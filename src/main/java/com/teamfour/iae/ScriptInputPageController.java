package com.teamfour.iae;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ScriptInputPageController implements Initializable {

    @FXML
    MFXTextField inputScriptPath;

    @FXML
    MFXButton chooseButton;

    @FXML
    MFXButton continueButton;

    String exePath;

    File exeFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        inputScriptPath.setEditable(false);

        chooseButton.setOnAction(event -> OnChoose());
        continueButton.setOnAction(event -> OnContinue());

    }

    private void OnChoose(){

        exePath = Helpers.exeFileChooser(chooseButton, "Choose Your EXE File");
        exeFile = new File(exePath);
        inputScriptPath.setText(exePath);

    }

    private void OnContinue() {
        //Run Exe
        ProcessBuilder pb = new ProcessBuilder(exePath);
        pb.directory(new File(exeFile.getParent()));
        try {
            Process process = pb.start();

            // Read the output of the process
            InputStream is = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            ProjectManager.getInstance().getCurrentProject().setTestInput(br.readLine());

            // Wait for the process to finish
            br.close();
            int exitCode = process.waitFor();
            System.out.println("Process exited with code " + exitCode);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        Helpers.CloseStage(continueButton);

    }

}