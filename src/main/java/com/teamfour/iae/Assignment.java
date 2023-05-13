package com.teamfour.iae;

import java.io.Serializable;

public class Assignment implements Serializable {
    private int id;
    private String name;
    private String definition;
    private String testInput;
    private String expectedOutputFilePath;


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
}
