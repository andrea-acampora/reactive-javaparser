package pcd02.view;

import io.reactivex.rxjava3.subjects.PublishSubject;

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

}
