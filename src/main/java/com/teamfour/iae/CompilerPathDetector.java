package com.teamfour.iae;

import java.io.File;
import java.util.Optional;
public class CompilerPathDetector {

    public static String findInPath(String executableName) {
        String path = System.getenv("PATH");
        String[] paths = path.split(";");

        for (String directory : paths) {
            File executable = new File(directory, executableName + ".exe");
            if (executable.exists() && executable.isFile()) {
                return executable.getAbsolutePath();
            }
        }
        return "";
    }
}