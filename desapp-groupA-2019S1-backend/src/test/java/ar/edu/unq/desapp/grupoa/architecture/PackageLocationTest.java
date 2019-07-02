package ar.edu.unq.desapp.grupoa.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import javax.persistence.Entity;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;


@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "ar.edu.unq.desapp.grupoa")
public class PackageLocationTest {

    @ArchTest
    public static final ArchRule DAOs_must_reside_in_a_dao_package =
            classes().that().haveNameMatching(".*DAO").should().resideInAPackage("..persistence..")
                    .as("DAOs should reside in a package '..persistence..'");

    @ArchTest
    public static final ArchRule entities_must_reside_in_a_domain_package =
            classes().that().areAnnotatedWith(Entity.class).should().resideInAPackage("..model..")
                    .as("Entities should reside in a package '..model..'");
}
