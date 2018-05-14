package org.javaee7.arquillian.programmatic.jbosscli;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.arquillian.api.ServerSetup;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;

/**
 * Created by meynier on 02/05/2017.
 */
@RunWith(Arquillian.class)
@ServerSetup(InstallProperties.class)
public class MyServiceTest {

    @Inject
    private MyService myService;

    @Deployment
    public static Archive<?> createDeployment() {
        final PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml");
        File[] compileAndRuntime = pom.importCompileAndRuntimeDependencies().resolve().withTransitivity().asFile();
        File[] test = pom.importTestDependencies().resolve().withTransitivity().asFile();
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(Database.class,MyService.class)
                .addClass(InstallProperties.class)
                .addAsLibraries(compileAndRuntime)
                .addAsLibraries(test)
                .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }

    @Test
    public void shouldGetProperties(){
        myService.getAll();
    }



}
