package mozell.learn.javatest;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class ArchTest {

    @Test
    void packageDependencyTests() {
        JavaClasses classes = new ClassFileImporter().importPackages("mozell.learn.mockitoPack");

        // ..domain.. 패키지에 있는 클래스는 ..study.., ..member.., ..domain에서 참조 가능.
        ArchRule domainPackageRule = classes().that().resideInAPackage("..domain..")
                .should().onlyBeAccessed().byClassesThat()
                .resideInAnyPackage("..study..", "..member..", "..domain..");

        domainPackageRule.check(classes);

        // (반대로) ..domain.. 패키지는 ..member.. 패키지를 참조하지 못한다.
        ArchRule memberPackageRule = noClasses().that().resideInAPackage("..domain..")
                .should().accessClassesThat().resideInAPackage("..member..");

        memberPackageRule.check(classes);

        // ..study.. 패키지에 있는 클래스는 ..study.. 에서만 참조 가능.
        ArchRule studyPackageRule = noClasses().that().resideOutsideOfPackage("..study..")
                .should().accessClassesThat().resideInAPackage("..study..");

        studyPackageRule.check(classes);

        // 순환 참조 없어야 한다.
        ArchRule freeOfCycles = slices().matching("..mockitoPack.(*)..")
                .should().beFreeOfCycles();

        freeOfCycles.check(classes);
    }
}
