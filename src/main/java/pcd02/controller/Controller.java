package pcd02.controller;

import pcd02.lib.ProjectAnalyzer;
import pcd02.lib.ProjectAnalyzerImpl;

public class Controller implements Observer {

    private final ProjectAnalyzer lib;

    public Controller() {
        this.lib = new ProjectAnalyzerImpl();
    }

    @Override
    public void notifyStart(final String path) {
        this.lib.analyzeProject(path, "my-topic");
    }

    @Override
    public void notifyStop() {

    }
}
