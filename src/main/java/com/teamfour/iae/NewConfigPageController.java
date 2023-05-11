package com.teamfour.iae;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
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
    MFXButton cancelButton;

    @FXML
    MFXButton importButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }
}
