package com.teamfour.iae;

import java.io.*;

public class DataManager {
    public void SerializeObject(Object object, String path) throws IOException {
        // Create the directory if it doesn't exist
        File directory = new File(path).getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }

        FileOutputStream fileOutputStream = new FileOutputStream(path);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public Object DeserializeObject(String path) throws IOException, ClassNotFoundException {

        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object o = objectInputStream.readObject();
        objectInputStream.close();

        return o;

    }

}
