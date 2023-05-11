package com.teamfour.iae;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {

    @FXML
    MenuBar MenuBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    public void OnNewProject() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("new-project-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("New Project");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void OnOpenProject(){




    }
    @FXML
    public void OnSaveProject(){



    }
    @FXML
    public void OnProjectSettings(){



    }

    @FXML
    public void OnNewConfiguration() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("new-config-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 375);
        Stage stage = new Stage();
        stage.setTitle("New Configuration");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();


    }

    @FXML
    public void OnEditConfiguration() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("edit-config-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 375);
        Stage stage = new Stage();
        stage.setTitle("Edit Configuration");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void OnImportConfiguration(){

        Configuration deserialized;
        try {
            DataManager manager = new DataManager();
            deserialized = (Configuration) manager.DeserializeObject(fileChooser());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        setCurrentConfiguration(deserialized);
    }

    private void setCurrentConfiguration(Configuration config) {
        CurrentConfiguration currentConfig = CurrentConfiguration.getInstance();
        currentConfig.getConfiguration().setName(config.getName());
        currentConfig.getConfiguration().setCompilerPath(config.getCompilerPath());
        currentConfig.getConfiguration().setExecutableName(config.getExecutableName());
        currentConfig.getConfiguration().setMainFileName(config.getMainFileName());
        currentConfig.getConfiguration().getCompilerParameters().clear();
        currentConfig.getConfiguration().getCompilerParameters().addAll(config.getCompilerParameters());
    }

    private String fileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Compiler");

        File initialDirectory = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(initialDirectory);

        Stage stage = (Stage) MenuBar.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        return selectedFile.getAbsolutePath();
    }

    @FXML
    public void OnExportConfiguration(){



    }

    @FXML
    public void OnAbout(){



    }

    @FXML
    public void OnDocumentation(){



    }

}