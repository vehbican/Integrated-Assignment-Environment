package com.teamfour.iae;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProjectManager {

    private final ArrayList<Configuration> importedConfigurations;
    private Configuration currentConfiguration;

    private ProjectManager() {

        File configsDir = new File("configs");
        if(!configsDir.exists()){configsDir.mkdirs();}

        currentConfiguration = new Configuration();
        try {
            File file = new File("configs/configs.txt");
            if (file.isFile()){

                importedConfigurations = (ArrayList<Configuration>) DataManager.DeserializeObject("configs/configs.txt");

            }else {

                importedConfigurations = new ArrayList<>();

            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final class InstanceHolder {
        private static final ProjectManager instance = new ProjectManager();
    }

    public static ProjectManager getInstance() {
        return InstanceHolder.instance;
    }

    public void setCurrentConfiguration(Configuration currentConfiguration) {this.currentConfiguration = currentConfiguration;}

    public Configuration getCurrentConfiguration() {
        return currentConfiguration;
    }

    public ArrayList<Configuration> getImportedConfigurations() {
        return importedConfigurations;
    }
}