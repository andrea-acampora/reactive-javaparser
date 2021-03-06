package pcd02.app;

import io.reactivex.rxjava3.subjects.PublishSubject;
import pcd02.controller.Controller;
import pcd02.view.View;

public class Main {

    public static void main(String[] args) {
        PublishSubject<String> clickStream = PublishSubject.create();
        View view = new View(clickStream);
        Controller controller = new Controller(view, clickStream);
        controller.init();
        view.start();
    }
}
