package com.teamfour.iae;

import java.io.Serializable;
import java.util.List;

public class Project implements Serializable {
    private int id;
    private String name;
    private Configuration configuration;
    private String zippedSubmissionsDirectory; // directory to the zip files
    private String extractedSubmissionsDirectory; // directory after unzip
    private Assignment assignment;
    private List<Submission> submissions;


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

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }
}
