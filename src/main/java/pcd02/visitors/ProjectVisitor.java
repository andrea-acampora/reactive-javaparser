package pcd02.visitors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import io.reactivex.rxjava3.core.ObservableEmitter;
import pcd02.interfaces.ProjectElem;
import pcd02.reports.ElemType;
import pcd02.reports.ProjectElemImpl;

import java.util.HashSet;
import java.util.Set;

public class ProjectVisitor extends VoidVisitorAdapter<ObservableEmitter<ProjectElem>> {

    private final Set<String> packages;

    public ProjectVisitor() {
        this.packages = new HashSet<>();
    }


    public void visit(CompilationUnit cu,  ObservableEmitter<ProjectElem> emitter) {
        super.visit(cu, emitter);
    }

    public void visit(PackageDeclaration pd, ObservableEmitter<ProjectElem> emitter) {
        super.visit(pd, emitter);
        if (packages.add(pd.getNameAsString())) {
            ProjectElem projectElem = new ProjectElemImpl();
            projectElem.setType(ElemType.PACKAGE);
            emitter.onNext(projectElem);
        }
    }

    public void visit(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, ObservableEmitter<ProjectElem> emitter) {
            super.visit(classOrInterfaceDeclaration, emitter);
            ProjectElem projectElem = new ProjectElemImpl();
            if (classOrInterfaceDeclaration.isInterface()) {
                projectElem.setType(ElemType.INTERFACE);
            } else {
                projectElem.setType(ElemType.CLASS);
            }
            emitter.onNext(projectElem);
    }

    public void visit(MethodDeclaration md, ObservableEmitter<ProjectElem> emitter) {
            super.visit(md, emitter);
            ProjectElem projectElem = new ProjectElemImpl();
            projectElem.setType(ElemType.METHOD);
            emitter.onNext(projectElem);
    }

    public void visit(FieldDeclaration fd, ObservableEmitter<ProjectElem> emitter) {
            super.visit(fd, emitter);
            ProjectElem projectElem = new ProjectElemImpl();
            projectElem.setType(ElemType.FIELD);
            emitter.onNext(projectElem);
    }

    private void log(String message) {
        System.out.println("[ Thread: " + Thread.currentThread().getName() + " ]" + ": " + message);
    }
}
