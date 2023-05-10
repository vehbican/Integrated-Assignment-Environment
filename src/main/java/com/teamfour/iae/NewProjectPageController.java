package com.teamfour.iae;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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
    MFXComboBox<String> inputMethodComboBox;

    @FXML
    MFXTextField expectedOutputFileInput;

    @FXML
    MFXButton chooseExpectedOutputFileButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }



}
