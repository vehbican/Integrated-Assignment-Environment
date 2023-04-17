package com.teamfour.iae;

import java.io.Serializable;

public class Student implements Serializable {
    private int id;
    private String studentID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}
