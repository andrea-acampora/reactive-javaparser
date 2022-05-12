package pcd02.controller;

import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import org.reactivestreams.Subscriber;
import pcd02.interfaces.ProjectElem;
import pcd02.lib.ProjectAnalyzer;
import pcd02.lib.ProjectAnalyzerImpl;

public class Controller {

    private final ProjectAnalyzer lib;
    private final PublishSubject<String> clickStream;
    private Subscriber<ProjectElem> subscriber;

    public Controller(PublishSubject<String> clickStream) {
        this.lib = new ProjectAnalyzerImpl();
        this.clickStream = clickStream;
        this.clickStream.observeOn(Schedulers.computation())
            .subscribe((res) -> {
                if(res.equals("stop")){
                    this.stop();
                }else {
                    this.start(res);
                }
        });
    }

    private void stop() {
    }

    private void start(String res) {
        lib.analyzeProject(res);
    }
}
