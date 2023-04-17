package com.teamfour.iae;

import java.io.*;
import java.util.zip.*;

public class ZipExtractor {

    public static void extractZipFiles(String sourceFolder, String destinationFolder) {
        File folder = new File(sourceFolder);
        File[] fileList = folder.listFiles();
        byte[] buffer = new byte[1024];

        assert fileList != null;
        for (File file : fileList) {
            if (file.isFile() && file.getName().endsWith(".zip")) {
                try {
                    ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
                    ZipEntry zipEntry = zipInputStream.getNextEntry();

                    while (zipEntry != null) {
                        String fileName = zipEntry.getName();
                        System.out.println(fileName);
                        System.out.println(file.getName());
                        File newFile = new File(destinationFolder + File.separator + fileName);

                        // create all non-existing folders
                        new File(newFile.getParent()).mkdirs();

                        FileOutputStream fileOutputStream = new FileOutputStream(newFile);

                        int len;
                        while ((len = zipInputStream.read(buffer)) > 0) {
                            fileOutputStream.write(buffer, 0, len);
                        }

                        fileOutputStream.close();
                        zipEntry = zipInputStream.getNextEntry();
                    }

                    zipInputStream.closeEntry();
                    zipInputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

