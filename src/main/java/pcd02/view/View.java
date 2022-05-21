package pcd02.view;

import io.reactivex.rxjava3.subjects.PublishSubject;
import pcd02.interfaces.ProjectElem;

public class View {

    private final ProjectAnalyzerGUI gui;
    private final PublishSubject<String> clickStream;

    public View(PublishSubject<String> clickStream) {
        this.gui = new ProjectAnalyzerGUI(clickStream);
        this.clickStream = clickStream;
    }

    public void start() {
        this.gui.start();
    }

    public void notifyElement(ProjectElem projectElem) {
        this.gui.notifyElement(projectElem);
    }

    public void resetCounter(){

    }
}
