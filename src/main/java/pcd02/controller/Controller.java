package pcd02.controller;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import pcd02.lib.ProjectAnalyzer;
import pcd02.lib.ProjectAnalyzerImpl;
import pcd02.view.View;

import java.io.IOException;

public class Controller {

    private final ProjectAnalyzer lib;
    private final PublishSubject<String> clickStream;
    private final View view;
    private Disposable disposable;
    private Disposable clickDisposable;

    public Controller(View view, PublishSubject<String> clickStream) {
        this.lib = new ProjectAnalyzerImpl();
        this.view = view;
        this.clickStream = clickStream;
    }

    private void stop() {
        disposable.dispose();
        clickDisposable.dispose();
    }

    private void start(String res) throws IOException {
        this.disposable = lib.analyzeProject(res)
                .onBackpressureBuffer(10_000, () -> {
                    System.out.println("Observable too fast !");
                    this.disposable.dispose();
                })
                .subscribeOn(Schedulers.computation())
                .subscribe(view::notifyElement);
       this.registerOnButtonClick();
    }

    public void init(){
        this.registerOnButtonClick();
    }

    public void registerOnButtonClick(){
        this.clickDisposable = this.clickStream
                .observeOn(Schedulers.computation())
                .subscribe((res) -> {
                    if(res.equals("stop")){
                        this.stop();
                    }else {
                        this.start(res);
                    }
                });
    }
}
