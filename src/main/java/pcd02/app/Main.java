package pcd02.app;

import pcd02.controller.Controller;
import pcd02.view.View;

public class Main {

    public static void main(String[] args) {

        Controller controller = new Controller(vertx);
        View view = new View();
        view.addObserver(controller);
    }
}
