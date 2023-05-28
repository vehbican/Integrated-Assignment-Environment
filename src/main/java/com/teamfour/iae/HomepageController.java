package com.teamfour.iae;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @FXML
    MFXComboBox<Submission> submissionComboBox;

    @FXML
    MFXButton showReportButton;

    @FXML
    TextArea submissionOutputLog;

    @FXML
    TextArea expectedOutputLog;

    @FXML
    ImageView resultImage;

    @FXML
    Label resultText;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        expectedOutputLog.setEditable(false);
        submissionOutputLog.setEditable(false);

        runButton.setOnAction(event -> OnRun());

        showReportButton.setOnAction(event -> OnShowReport());

        submissionComboBox.setOnMouseEntered(event ->{
            if (ProjectManager.getInstance().getCurrentProject().getSubmissions() != null){

                submissionComboBox.getItems().setAll(ProjectManager.getInstance().getCurrentProject().getSubmissions());

            }
        });

    }

    public void OnRun(){
        File submissionsDir = new File(Helpers.submissionsDir);
        File[] submissions = submissionsDir.listFiles();

        ArrayList<Submission> submissionList = CreateSubmissions(Objects.requireNonNull(submissions));

        ProjectManager.getInstance().getCurrentProject().setSubmissions(submissionList);

        try {
            ProcessSubmissions(submissionList);
            RunExecutables(submissionList);
            CreateReports(submissionList);
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


        ProjectManager.getInstance().getCurrentProject().setSubmissions(submissions);

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
            case VIA_EXECUTABLE -> {

                // Input via a script that returns a string

                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("script-input-page.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Program Input");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setResizable(false);
                stage.showAndWait();

            }

        }

        if (ProjectManager.getInstance().getCurrentProject().getTestInput().isBlank()){return;}

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

        ProjectManager.getInstance().getCurrentProject().setSubmissions(submissions);

    }

    private void CreateReports(ArrayList<Submission> submissions) throws IOException {

        String expectedOutputFile = ProjectManager.getInstance().getCurrentProject().getExpectedOutputFilePath();

        for (Submission s:submissions){

            Report report = new Report();

            String studentOutputPath = s.getDirectory()+"/"+Helpers.runtimeOutputLog;
            String studentErrorPath = s.getDirectory()+"/"+Helpers.runtimeErrorLog;
            String studentCompileTimeOutput = s.getDirectory()+"/"+Helpers.compileTimeOutputLog;
            String studentCompileTimeError = s.getDirectory()+"/"+Helpers.compileTimeErrorLog;

            if (Helpers.AreEqual(studentOutputPath,expectedOutputFile)){

                report.setResult(Result.PASSED);

            }else {

                report.setResult(Result.FAILED);

            }


            report.setRuntimeOutputLog(Helpers.ReadFileIntoString(studentOutputPath));
            report.setRuntimeErrorLog(Helpers.ReadFileIntoString(studentErrorPath));
            report.setCompileTimeOutputLog(Helpers.ReadFileIntoString(studentCompileTimeOutput));
            report.setCompileTimeErrorLog(Helpers.ReadFileIntoString(studentCompileTimeError));

            report.setExpectedOutput(Helpers.ReadFileIntoString(expectedOutputFile));

            s.setReport(report);

        }

        ProjectManager.getInstance().getCurrentProject().setSubmissions(submissions);


    }

    private void OnShowReport(){

        if (submissionComboBox.getValue() == null) return;

        Submission selected = submissionComboBox.getValue();

        if(selected.getReport().getCompileTimeErrorLog().isBlank()){

            submissionOutputLog.setText(selected.getReport().getRuntimeOutputLog());

        }else {

            submissionOutputLog.setText(selected.getReport().getCompileTimeErrorLog());

        }

        expectedOutputLog.setText(selected.getReport().getExpectedOutput());
        resultText.setText(selected.getReport().getResult().toString());

        Image i = new Image(Objects.requireNonNull(App.class.getResource(selected.getReport().getResult().toString().trim()+".png")).toExternalForm());
        resultImage.setImage(i);

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

        expectedOutputLog.clear();
        submissionOutputLog.clear();
        resultText.setText("");
        resultImage.setImage(null);
        submissionComboBox.clear();

    }

    @FXML
    public void OnOpenProject(){

        Project deserialized;
        try {
            deserialized = (Project) DataManager.DeserializeObject(Helpers.fileChooser(MenuBar,"Select Project"));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (deserialized != null && deserialized.getClass().equals(Project.class)){

            ProjectManager.getInstance().setCurrentProject(deserialized);
            ProjectManager.getInstance().setCurrentConfiguration(deserialized.getConfiguration());

            boolean configFound = false;

            for (Configuration c:ProjectManager.getInstance().getImportedConfigurations()){

                if (c.getName().equals(deserialized.getConfiguration().getName())){

                    configFound = true;
                    break;

                }

            }

            if (!configFound){
                ProjectManager.getInstance().getImportedConfigurations().add(deserialized.getConfiguration());
            }
            projectInfo.setText(deserialized.showProjectInfo());
            configInfo.setText(deserialized.getConfiguration().ConfigInfo());
            Helpers.showAlert(Alert.AlertType.INFORMATION,"Configuration Imported",deserialized.showProjectInfo());

            expectedOutputLog.clear();
            submissionOutputLog.clear();
            resultText.setText("");
            resultImage.setImage(null);
            submissionComboBox.clear();

        }else {

            Helpers.showAlert(Alert.AlertType.ERROR,"Import Error","Import failed.");

        }


    }
    @FXML
    public void OnSaveProject(){

        Project p = ProjectManager.getInstance().getCurrentProject();

        if (p == null) {
            Helpers.showAlert(Alert.AlertType.WARNING, "Project Error", "Not found an active project.");
            return;
        }

        //save
        String path = Helpers.projectsDir+"/"+p.getName().trim().replaceAll(" ","-")+".txt";
        try {
            DataManager.SerializeObject(p,path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    public void OnProjectSettings() throws IOException {

        if (ProjectManager.getInstance().getCurrentProject().getName() == null){
            Helpers.showAlert(Alert.AlertType.WARNING,"Not Found","There is not an active project.");
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("project-settings-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);
        Stage stage = new Stage();
        stage.setTitle("Project Settings");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();

        ProjectSettingsPageController settingsPageController = fxmlLoader.getController();

        settingsPageController.homepageController = this;

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
    public void OnDocumentation(){
        File file = new File("Help.pdf");
        if (file.exists()){
            try{
                new ProcessBuilder("cmd","/c",file.getAbsolutePath()).start();
            }
            catch (IOException e ){
                e.printStackTrace();
            }
        }



    }

}