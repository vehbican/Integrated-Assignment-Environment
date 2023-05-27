package com.teamfour.iae;

import java.io.Serializable;
import java.util.List;

public class Project implements Serializable {
    private int id;
    private String name;
    private Configuration configuration;
    private String zippedSubmissionsDirectory; // directory to the zip files
    private String extractedSubmissionsDirectory; // directory after unzip
    private String definition;
    private String testInput;
    private String expectedOutputFilePath;

    private InputMethods inputMethod;

    private List<Submission> submissions;




    public enum InputMethods{

        MANUAL,
        VIA_EXECUTABLE

    }


    public String showProjectInfo(){

        StringBuilder b = new StringBuilder();

        b.append("Project Name: ").append(name).append("\n");
        b.append("Zip Files Directory: ").append(zippedSubmissionsDirectory).append("\n");
        b.append("Submissions Directory: ").append(extractedSubmissionsDirectory).append("\n");
        b.append("Input Method: ").append(inputMethod).append("\n");
        b.append("Expected Output File: ").append(expectedOutputFilePath).append("\n");

        return b.toString();

    }

    // GETTERS - SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getZippedSubmissionsDirectory() {
        return zippedSubmissionsDirectory;
    }

    public void setZippedSubmissionsDirectory(String zippedSubmissionsDirectory) {
        this.zippedSubmissionsDirectory = zippedSubmissionsDirectory;
    }

    public String getExtractedSubmissionsDirectory() {
        return extractedSubmissionsDirectory;
    }

    public void setExtractedSubmissionsDirectory(String extractedSubmissionsDirectory) {
        this.extractedSubmissionsDirectory = extractedSubmissionsDirectory;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getTestInput() {
        return testInput;
    }

    public void setTestInput(String testInput) {
        this.testInput = testInput;
    }

    public String getExpectedOutputFilePath() {
        return expectedOutputFilePath;
    }

    public void setExpectedOutputFilePath(String expectedOutputFilePath) {
        this.expectedOutputFilePath = expectedOutputFilePath;
    }

    public InputMethods getInputMethod() {
        return inputMethod;
    }

    public void setInputMethod(InputMethods inputMethod) {
        this.inputMethod = inputMethod;
    }
}
