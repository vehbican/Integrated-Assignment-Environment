package com.teamfour.iae;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProjectManager {

    private final ArrayList<Configuration> importedConfigurations;
    private Configuration currentConfiguration;

    private Project currentProject;

    private final ProcessManager processManager;

    private ProjectManager() {

        currentProject = new Project();
        processManager = new ProcessManager();

        File configsDir = new File(Helpers.configsDir);
        if(!configsDir.exists()){configsDir.mkdirs();}

        File submissionsDir = new File(Helpers.submissionsDir);
        if (!submissionsDir.exists()){submissionsDir.mkdirs();}


        currentConfiguration = new Configuration();
        currentConfiguration.setName("InitialConfig");
        try {
            File file = new File(Helpers.configsDir+"/configs.txt");
            if (file.isFile()){

                importedConfigurations = (ArrayList<Configuration>) DataManager.DeserializeObject(Helpers.configsDir+"/configs.txt");

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

    public Project getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
    }

    public ProcessManager getProcessManager() {
        return processManager;
    }
}