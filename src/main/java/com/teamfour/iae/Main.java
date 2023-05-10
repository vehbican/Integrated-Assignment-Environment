package com.teamfour.iae;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Configuration config = new Configuration();
        /*config.setName("C Programming Language Configuration");
        config.setCompilerPath("C:\\msys64\\mingw64\\bin\\gcc.exe");
        config.setExecutableName("main.exe");
        config.setMainFileName("main.c");
        config.getCompilerParameters().addAll(List.of(new String[]{config.getCompilerPath(),"-o",config.getExecutableName()}));*/

        config.setName("Java Programming Language Configuration");
        //config.setCompilerPath("C:\\Program Files\\Java\\jdk-17.0.2\\bin\\javac.exe");
        config.setExecutableName("Main.exe");
        config.setMainFileName("main.java");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Configuration Name:");
        config.setName(scanner.nextLine());
        System.out.print("Compiler Parameters:");
        String cp = scanner.nextLine();
        //config.getCompilerParameters().add(config.getCompilerPath());
        config.getCompilerParameters().addAll(List.of(cp.trim().split(" ")));
        System.out.print("Runtime Parameters:");
        String rp = scanner.nextLine();
        config.setRuntimeParameters(List.of(rp.trim().split(" ")));


        Assignment assignment = new Assignment();
        assignment.setId(1);
        assignment.setName("Sorting Strings");
        assignment.setDefinition("A list of strings must be sorted in ascending order by using C language." +
                "The list of strings must be passed to the main via the command line arguments." +
                "The assignment must be saved as a main.c file.");

        assignment.setTestInput("a c d e b");
        assignment.setExpectedOutput("a\nb\nc\nd\ne");

        Project project = new Project();
        project.setId(1);
        project.setName("C Project");
        project.setConfiguration(config);
        project.setAssignment(assignment);
        project.setZippedSubmissionsDirectory("src\\main\\resources\\com\\teamfour\\iae\\zips");
        project.setExtractedSubmissionsDirectory("src\\main\\resources\\com\\teamfour\\iae\\extracteds");

        //ZipExtractor.extractZipFiles(project.getZippedSubmissionsDirectory(), project.getExtractedSubmissionsDirectory());

        //Compile Code
        ProcessManager processManager = new ProcessManager();
        String submissionDirectory = "src/main/resources/com/teamfour/iae/extracteds/20200602036";
        try {
            processManager.CompileFile(project.getConfiguration(),submissionDirectory);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        //Run Exe
        try {
            processManager.RunExecutable(config,submissionDirectory);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Serialize Project
        /*DataManager d = new DataManager();
        try {
            //d.SerializeObject(project,"project.txt");
            Project p = (Project) d.DeserializeObject("project.txt");
            System.out.println(p.getAssignment().getDefinition());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/


    }
}