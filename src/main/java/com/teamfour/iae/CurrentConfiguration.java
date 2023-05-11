package com.teamfour.iae;

public class CurrentConfiguration {
    private static CurrentConfiguration instance;
    private Configuration configuration;

    private CurrentConfiguration() {
        configuration = new Configuration();
    }

    public static CurrentConfiguration getInstance() {
        if (instance == null) {
            synchronized (CurrentConfiguration.class) {
                if (instance == null) {
                    instance = new CurrentConfiguration();
                }
            }
        }
        return instance;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}