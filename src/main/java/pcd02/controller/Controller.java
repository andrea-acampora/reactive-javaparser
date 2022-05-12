package pcd02.controller;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import pcd02.interfaces.ProjectElem;
import pcd02.lib.ProjectAnalyzer;
import pcd02.lib.ProjectAnalyzerImpl;
import pcd02.view.View;

public class Controller {

    private final ProjectAnalyzer lib;
    private final PublishSubject<String> clickStream;
    //private final MyObserver observer;
    private final View view;
    private Disposable disposable;

    public Controller(View view, PublishSubject<String> clickStream) {
        this.lib = new ProjectAnalyzerImpl();
        this.view = view;
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
        System.out.println("STOP");
        disposable.dispose();
    }

    private void start(String res) {
        disposable = lib.analyzeProject(res).subscribeOn(Schedulers.computation()).subscribe(r -> {
            view.notifyElement(r);
            System.out.println("r = " + r);
        });
    }
}
