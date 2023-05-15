package com.teamfour.iae;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProcessManager {

    ProcessBuilder processBuilder;
    File outputLog;
    File errorLog;



    public void CompileFile(Configuration configuration, String submissionDirectory) throws IOException, InterruptedException {

        // Create a new process builder for calling compiler
        processBuilder = new ProcessBuilder();
        processBuilder.directory(new File(submissionDirectory));
        processBuilder.command(configuration.getCompilerParameters());

        //Create report
        errorLog = new File(submissionDirectory+"\\compileTimeError.log");
        processBuilder.redirectError(errorLog);

        outputLog = new File(submissionDirectory+"\\compileTimeOutput.log");
        processBuilder.redirectOutput(outputLog);

        // Start the process
        Process process = processBuilder.start();

        // Read the output of the process
        InputStream is = process.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        // Wait for the process to finish
        int exitCode = process.waitFor();
        System.out.println("Process exited with code " + exitCode);

    }

    public void RunExecutable(Configuration configuration,String submissionDirectory) throws IOException, InterruptedException {

        processBuilder = new ProcessBuilder();
        processBuilder.directory(new File(String.valueOf(Paths.get(submissionDirectory))));

        List<String> commands = new ArrayList<>();

        commands.addAll(configuration.getRuntimeParameters());

        // gcc, g++ and dotnet compilers find the path only in this way.
        if(configuration.getCompilerParameters().contains("gcc") || configuration.getCompilerParameters().get(0).contains("csc.exe") ||configuration.getCompilerParameters().contains("g++") ){

            String tmp = commands.get(0);
            commands.set(0,submissionDirectory+"/"+tmp);

        }

        processBuilder.command(commands);

        // Create report
        outputLog = new File(submissionDirectory + "\\runtimeOutput.log");
        processBuilder.redirectOutput(outputLog);

        errorLog = new File(submissionDirectory + "\\runtimeError.log");
        processBuilder.redirectError(errorLog);

        // Start the process
        Process process = processBuilder.start();


        // Read the output of the process
        InputStream is = process.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        // Wait for the process to finish
        int exitCode = process.waitFor();
        System.out.println("Process exited with code " + exitCode);

    }

}
