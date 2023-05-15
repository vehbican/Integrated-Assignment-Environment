package com.teamfour.iae;

import java.io.Serializable;

public class Report implements Serializable {
    private Result result;
    private String runtimeOutputLog;
    private String runtimeErrorLog;

    private String compileTimeOutputLog;
    private String compileTimeErrorLog;

    private String expectedOutput;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getRuntimeOutputLog() {
        return runtimeOutputLog;
    }

    public void setRuntimeOutputLog(String runtimeOutputLog) {
        this.runtimeOutputLog = runtimeOutputLog;
    }

    public String getRuntimeErrorLog() {
        return runtimeErrorLog;
    }

    public void setRuntimeErrorLog(String runtimeErrorLog) {
        this.runtimeErrorLog = runtimeErrorLog;
    }

    public String getCompileTimeOutputLog() {
        return compileTimeOutputLog;
    }

    public void setCompileTimeOutputLog(String compileTimeOutputLog) {
        this.compileTimeOutputLog = compileTimeOutputLog;
    }

    public String getCompileTimeErrorLog() {
        return compileTimeErrorLog;
    }

    public void setCompileTimeErrorLog(String compileTimeErrorLog) {
        this.compileTimeErrorLog = compileTimeErrorLog;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }
}

