package pcd02.controller;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import pcd02.interfaces.ProjectElem;
import pcd02.view.View;

public class MyObserver implements Observer<ProjectElem> {

    private final View view;

    public MyObserver(View view) {
        this.view = view;
    }


    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull ProjectElem projectElem) {
        this.view.notifyElement(projectElem);
    }

    @Override
    public void onError(@NonNull Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
