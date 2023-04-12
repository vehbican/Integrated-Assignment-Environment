package com.teamfour.iae;

import java.io.*;

public class CCompiler {
    public static void main(String[] args) {

        // Compiles hello.c script
        try {
            CompileFile("SortingStrings.c");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Runs exe
        try {
            String[] commandlineArgs = {"main.exe","a","e","f", "g", "h", "c", "d", "b", "k", "m", "z", "y"};
            RunExecutable(commandlineArgs);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        //Check log files at the end

    }

    static void RunExecutable(String[] args) throws IOException, InterruptedException {

        ProcessBuilder pb = new ProcessBuilder( args);

        File log = new File("runtimeOutput.log");
        pb.redirectOutput(log);

        File errorLog = new File("runtimeError.log");
        pb.redirectError(errorLog);

        Process process = pb.start();

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

    static void CompileFile(String path) throws IOException, InterruptedException {

        // Create a new process builder for calling GCC compiler
        ProcessBuilder pb = new ProcessBuilder("gcc", "-o", "main", path);

        // Redirect error stream to standard output
        // pb.redirectErrorStream(true);

        File errorLog = new File("compileTimeError.log");
        pb.redirectError(errorLog);

        File outputLog = new File("compileTimeOutput.log");
        pb.redirectOutput(outputLog);

        // Start the process
        Process process = pb.start();

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


