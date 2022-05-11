package pcd02.view;

import pcd02.controller.Observer;

public class View {

    private final ProjectAnalyzerGUI gui;

    public View() {
        this.gui = new ProjectAnalyzerGUI();
    }

    public void addObserver(Observer observer) {
        this.gui.addObserver(observer);
    }

    public void start() {
        this.gui.start();
    }

}
