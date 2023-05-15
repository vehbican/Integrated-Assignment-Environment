package com.teamfour.iae;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;

public class ScriptInputPageController implements Initializable {

    @FXML
    MFXTextField inputScriptPath;

    @FXML
    MFXButton chooseButton;

    @FXML
    MFXButton continueButton;


    String scriptPath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        inputScriptPath.setEditable(false);

        chooseButton.setOnAction(event -> OnChoose());
        continueButton.setOnAction(event -> OnContinue());

    }

    private void OnChoose(){

        scriptPath = Helpers.fileChooser(chooseButton,"Choose Script");
        inputScriptPath.setText(scriptPath);

    }

    private void OnContinue() {

        Helpers.showAlert(Alert.AlertType.INFORMATION,"Information","Not implemented,yet. Please try manual input method.");
        Helpers.CloseStage(continueButton);

    }

}
