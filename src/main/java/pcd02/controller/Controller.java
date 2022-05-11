package pcd02.controller;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import pcd02.lib.ProjectAnalyzer;
import pcd02.lib.ProjectAnalyzerImpl;

public class Controller{

    private final ProjectAnalyzer lib;
    private final PublishSubject<String> clickStream;

    public Controller(PublishSubject<String> clickStream) {
        this.lib = new ProjectAnalyzerImpl();
        this.clickStream = clickStream;
        this.clickStream.observeOn(Schedulers.computation())
            .subscribe((res) -> {
                if(res.equals("stop")){
                    this.stop();
                }else{
                    this.start(res);
                }
        });
    }

    private void stop() {
        System.out.println("STOP " + Thread.currentThread());
    }

    private void start(String res) {
        System.out.println("START " + Thread.currentThread());
        lib.analyzeProject(res);
    }

}
