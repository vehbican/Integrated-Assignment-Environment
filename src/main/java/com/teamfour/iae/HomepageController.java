package com.teamfour.iae;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {

    @FXML
    MenuBar MenuBar;

    @FXML
    Text projectInfo;

    @FXML
    Text configInfo;

    @FXML
    MFXButton runButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        runButton.setOnAction(event -> OnRun());

    }

    public void OnRun(){
        File submissionsDir = new File(Helpers.submissionsDir);
        File[] submissions = submissionsDir.listFiles();

        ArrayList<Submission> submissionList = CreateSubmissions(Objects.requireNonNull(submissions));

        ProjectManager.getInstance().getCurrentProject().setSubmissions(submissionList);

        try {
            ProcessSubmissions(submissionList);
            RunExecutables(submissionList);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private ArrayList<Submission> CreateSubmissions(File[] files){

        ArrayList<Submission> submissions = new ArrayList<>();
        Configuration currentConfig = ProjectManager.getInstance().getCurrentProject().getConfiguration();

        for (File s : files) {


            if (s.isDirectory()) {
                System.out.println("Directory: " + s.getName());

                Student student = new Student();
                student.setStudentID(s.getName().trim());

                Submission submission = new Submission();
                submission.setStudent(student);
                submission.setDirectory(s.getPath());

                submissions.add(submission);

                CreateSubmissions(Objects.requireNonNull(s.listFiles()));
            }
            else {
                System.out.println("File: " + s.getName());

                if (s.getName().trim().equals(currentConfig.getMainFileName())){

                    System.out.println("Compiling " + s.getName().trim());
                    /*try {
                        ProjectManager.getInstance().getProcessManager().CompileFile(currentConfig,submission.getDirectory());
                        System.out.println("Compiled " + s.getName().trim());
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }*/

                }

            }
        }

        return submissions;

    }

    private void ProcessSubmissions(ArrayList<Submission> submissions) throws IOException, InterruptedException {

        Configuration currentConfig = ProjectManager.getInstance().getCurrentProject().getConfiguration();

        for (Submission s:submissions){

            ProjectManager.getInstance().getProcessManager().CompileFile(currentConfig,s.getDirectory());

        }

    }


    private void RunExecutables(ArrayList<Submission> submissions) throws IOException, InterruptedException {

        Configuration currentConfig = ProjectManager.getInstance().getCurrentProject().getConfiguration();

        switch (ProjectManager.getInstance().getCurrentProject().getInputMethod()){

            case MANUAL -> {

                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("manual-input-page.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Manual Input");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setResizable(false);
                stage.showAndWait();

            }
            case VIA_SCRIPT -> {

                // Input via a script that returns a string





            }

        }

        ArrayList<String> runtimeParameters = new ArrayList<>();

        // Temporary Solution for not exe files
        if (currentConfig.getMainFileName().contains(".java")){

            runtimeParameters.add("java");

        }else if (currentConfig.getMainFileName().contains(".py")){

            runtimeParameters.add("python");

        }

        runtimeParameters.add(currentConfig.getExecutableName().trim());
        runtimeParameters.addAll(List.of(ProjectManager.getInstance().getCurrentProject().getTestInput().split(" ")));

        currentConfig.setRuntimeParameters(runtimeParameters);

        ProjectManager.getInstance().getCurrentProject().setConfiguration(currentConfig);


        for (Submission s:submissions){

            ProjectManager.getInstance().getProcessManager().RunExecutable(currentConfig,s.getDirectory());

        }

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