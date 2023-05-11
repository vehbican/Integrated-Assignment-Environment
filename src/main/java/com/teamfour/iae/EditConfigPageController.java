package com.teamfour.iae;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ResourceBundle;

public class EditConfigPageController implements Initializable {

    @FXML
     MFXComboBox<Type> compilerName;
    @FXML
     MFXTextField compilerPathInput;

    @FXML
     MFXTextField compilerParametersInput;
    @FXML
     MFXButton editButton;

    @FXML
     MFXButton deleteButton;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



}
