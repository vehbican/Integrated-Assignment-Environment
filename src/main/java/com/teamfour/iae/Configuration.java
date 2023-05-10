package com.teamfour.iae;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Configuration implements Serializable {
    private String name;
    private String compilerPath;
    private List<String> compilerParameters; //gcc -o main.exe main.c
    private List<String> runtimeParameters; //main.exe [input list]
    private String executableName;
    private String mainFileName;

    public Configuration(){
        compilerParameters = new ArrayList<>();

        //get compiler path from system environment variables automatically, kullanıcı path seçmeden önce otomatik bulunanlar yazdırılabilir
        String javacPath = findPathOfCompiler("javac");
        if (!javacPath.equals("")) {
            compilerPath = javacPath;
        }

        String gccPath = findPathOfCompiler("gcc");
        if (!gccPath.equals("")) {
            compilerPath = gccPath;
        }
    }

    // GETTERS - SETTERS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompilerPath() {
        return compilerPath;
    }

    public void setCompilerPath(String compilerPath) {
        this.compilerPath = compilerPath;
    }

    public List<String> getCompilerParameters() {
        return compilerParameters;
    }

    public void setCompilerParameters(List<String> compilerParameters) {
        this.compilerParameters = compilerParameters;
    }

    public String getExecutableName() {
        return executableName;
    }

    public void setExecutableName(String executableName) {
        this.executableName = executableName;
    }

    public String getMainFileName() {
        return mainFileName;
    }

    public void setMainFileName(String mainFileName) {
        this.mainFileName = mainFileName;
    }

    public List<String> getRuntimeParameters() {
        return runtimeParameters;
    }

    public void setRuntimeParameters(List<String> runtimeParameters) {
        this.runtimeParameters = runtimeParameters;
    }

    public static String findPathOfCompiler(String languageParameter) {
        String path = System.getenv("PATH");
        String[] paths = path.split(";");

        for (String directory : paths) {
            File executable = new File(directory, languageParameter + ".exe");
            if (executable.exists() && executable.isFile()) {
                return executable.getAbsolutePath();
            }
        }
        return "";
    }

}
