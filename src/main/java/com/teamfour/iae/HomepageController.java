package com.teamfour.iae;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {

    @FXML
    MenuBar MenuBar;

    @FXML
    Text projectInfo;

    @FXML
    Text configInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }


    @FXML
    public void OnNewProject() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("new-project-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);
        Stage stage = new Stage();
        stage.setTitle("New Project");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();

        NewProjectPageController newProjectPageController = fxmlLoader.getController();

        newProjectPageController.homepageController = this;

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
        Scene scene = new Scene(fxmlLoader.load(), 400, 450);
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
            deserialized = (Configuration) DataManager.DeserializeObject(Helpers.fileChooser(MenuBar,"Select Configuration"));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (deserialized != null && deserialized.getClass().equals(Configuration.class)){

            ProjectManager.getInstance().setCurrentConfiguration(deserialized);
            ProjectManager.getInstance().getImportedConfigurations().add(deserialized);
            Helpers.showAlert(Alert.AlertType.INFORMATION,"Configuration Imported",deserialized.ConfigInfo());

        }else {

            Helpers.showAlert(Alert.AlertType.ERROR,"Import Error","Import failed.");

        }

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