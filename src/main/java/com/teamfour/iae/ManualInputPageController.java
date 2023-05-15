package com.teamfour.iae;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ManualInputPageController implements Initializable {

    @FXML
    MFXTextField inputText;

    @FXML
    MFXButton continueButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        continueButton.setOnAction(event -> OnContinue());

    }

    private void OnContinue(){

        ProjectManager.getInstance().getCurrentProject().setTestInput(inputText.getText().trim());
        Helpers.CloseStage(continueButton);
    }

}
