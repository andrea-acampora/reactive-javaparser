package pcd02.lib;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import pcd02.interfaces.*;
import pcd02.reports.*;
import pcd02.visitors.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class ProjectAnalyzerImpl implements ProjectAnalyzer {


    public ProjectAnalyzerImpl() {}

    @Override
    public Flowable<ClassReport> getClassReport(final String srcClassPath) {
        return Flowable.fromCallable( () -> {
            ClassReport classReport = new ClassReportImpl();
            try {
                ClassReportCollector collector = new ClassReportCollector();
                CompilationUnit cu = StaticJavaParser.parse(new File(srcClassPath));
                classReport.setSrcFullFileName(srcClassPath);
                collector.visit(cu, classReport);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return classReport;
        });
    }

    @Override
    public Flowable<InterfaceReport> getInterfaceReport(final String srcInterfacePath) {
        return Flowable.fromCallable( () -> {
            InterfaceReport interfaceReport = new InterfaceReportImpl();
            try {
                InterfaceReportCollector collector = new InterfaceReportCollector();
                CompilationUnit cu = StaticJavaParser.parse(new File(srcInterfacePath));
                interfaceReport.setSrcFullFileName(srcInterfacePath);
                collector.visit(cu, interfaceReport);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return interfaceReport;
        });
    }

    @Override
    public Flowable<PackageReport> getPackageReport(final String srcPackagePath) {
        return Flowable.fromCallable( () -> {
            PackageReport packageReport = new PackageReportImpl();
            try {
                SourceRoot sourceRoot = new SourceRoot(Paths.get(srcPackagePath));
                sourceRoot.tryToParse();
                List<CompilationUnit> compilationUnits = sourceRoot.getCompilationUnits();
                packageReport.setPackagePath(srcPackagePath);
                packageReport.setFullPackageName(compilationUnits.size() > 0 && compilationUnits.get(0).getPackageDeclaration().isPresent() ?
                        compilationUnits.get(0).getPackageDeclaration().get().getNameAsString() : srcPackagePath);
                PackageReportCollector packageReportCollector = new PackageReportCollector();
                compilationUnits.forEach(cu -> packageReportCollector.visit(cu, packageReport));
                packageReportCollector.searchMainClass(packageReport);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return packageReport;
        });
    }

    @Override
    public Flowable<ProjectReport> getProjectReport(final String srcProjectFolderPath) {
        return Flowable.fromCallable( () -> {
            ProjectReport projectReport = new ProjectReportImpl();
            try {
                SourceRoot sourceRoot = new SourceRoot(Paths.get(srcProjectFolderPath));
                sourceRoot.tryToParse();
                List<CompilationUnit> compilationUnits = sourceRoot.getCompilationUnits();
                ProjectReportCollector projectReportCollector = new ProjectReportCollector();
                compilationUnits.forEach(cu -> projectReportCollector.visit(cu, projectReport));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return projectReport;
        });
    }

    @Override
    public void analyzeProject(String srcProjectFolderName, Observer<ProjectElem> observer) {
        SourceRoot sourceRoot = new SourceRoot(Paths.get(srcProjectFolderName));
        try {
            sourceRoot.tryToParse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<CompilationUnit> compilationUnits = sourceRoot.getCompilationUnits();
        ProjectVisitor projectVisitor = new ProjectVisitor();
        Observable<ProjectElem> source = Observable.create(emitter -> compilationUnits.forEach(cu ->  projectVisitor.visit(cu, emitter)));
        source.subscribeWith(observer);
    }
}
