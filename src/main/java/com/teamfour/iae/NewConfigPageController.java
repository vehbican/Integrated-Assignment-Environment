package com.teamfour.iae;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    MFXComboBox<String> autoDetectedCompilers;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        createButton.setOnAction(event -> OnCreateButton());
        chooseCompilerPathButton.setOnAction(event -> OnChooseCompilerButton());

        populateAutoDetectedCompilers();
        autoDetectedCompilers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                compilerPathInput.setText(newValue);
            }
        });

        compilerPathInput.setEditable(false);

    }

    private void populateAutoDetectedCompilers() {
        List<String> detectedCompilers = getDetectedCompilers();
        autoDetectedCompilers.getItems().addAll(detectedCompilers);
    }

    private List<String> getDetectedCompilers() {
        List<String> detectedCompilers = new ArrayList<>();
        String javacPath = findPathOfCompiler("javac");
        if (!javacPath.equals("")) {
            detectedCompilers.add(javacPath);
        }

        String gccPath = findPathOfCompiler("gcc");
        if (!gccPath.equals("")) {
            detectedCompilers.add(gccPath);
        }

        String pythonPath = findPathOfCompiler("python");
        if (!pythonPath.equals("")) {
            detectedCompilers.add(pythonPath);
        }

        String csharpPath = findPathOfCompiler("csc");
        if (!csharpPath.equals("")) {
            detectedCompilers.add(csharpPath);
        }

        return detectedCompilers;
    }

    public static String findPathOfCompiler(String languageParameter) {
        String path = System.getenv("PATH");
        String[] paths = path.split(";");

        for (String directory : paths) {
            File executable = new File(directory, languageParameter + ".exe");
            if (executable.exists() && executable.isFile()) {
                return executable.getAbsolutePath();
            }
        }
        return "";
    }

    private void OnChooseCompilerButton() {
            String filePath = Helpers.fileChooser(chooseCompilerPathButton,"Select Compiler");
            compilerPathInput.setText(filePath);
    }

    private void OnCreateButton() {
        //create
        Configuration config = new Configuration();
        config.setName(configNameInput.getText());
        config.setCompilerPath(compilerPathInput.getText());
        config.setExecutableName(exeNameInput.getText());
        config.setMainFileName(mainFileInput.getText());
        config.getCompilerParameters().addAll(List.of(compilerParametersInput.getText().trim().split(" ")));


        //set
        ProjectManager.getInstance().setCurrentConfiguration(config);
        ProjectManager.getInstance().getImportedConfigurations().add(config);


        //save
        String path = Helpers.configsDir+"/"+configNameInput.getText()+".txt";
        try {
            DataManager.SerializeObject(config,path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        closeWindow();
    }
    private void closeWindow() {
        Stage stage = (Stage) createButton.getScene().getWindow();
        stage.close();
    }

}