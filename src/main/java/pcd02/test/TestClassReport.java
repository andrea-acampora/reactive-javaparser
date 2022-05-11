package pcd02.test;

import io.reactivex.rxjava3.core.Flowable;
import pcd02.interfaces.ClassReport;
import pcd02.lib.ProjectAnalyzer;
import pcd02.lib.ProjectAnalyzerImpl;

import java.io.FileNotFoundException;

public class TestClassReport {
    public static void main(String[] args) throws FileNotFoundException {
        ProjectAnalyzer projectAnalyzer = new ProjectAnalyzerImpl();
        Flowable<ClassReport> classReportFlowable = projectAnalyzer.getClassReport("src/main/java/pcd02/lib/ProjectAnalyzerImpl.java");
        classReportFlowable.subscribe((res) -> {
            System.out.println("Class name: " + res.getFullClassName());
            System.out.println("Path : " + res.getSrcFullFileName());
            System.out.println("methods : " + res.getMethodsInfo());
            System.out.println("fields : " + res.getFieldsInfo());
        });
    }
}
