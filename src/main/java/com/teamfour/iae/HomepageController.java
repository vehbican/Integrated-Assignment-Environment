package com.teamfour.iae;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {


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