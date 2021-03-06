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
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;

/**
 * Created by meynier on 02/05/2017.
 */
@RunWith(Arquillian.class)
@ServerSetup(PlayCli.class)
public class MyServiceTest {

    @Inject
    private MyService myService;

    @Deployment
    public static Archive<?> createDeployment() {
        final PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml");
        File[] test = pom.importTestDependencies().resolve().withTransitivity().asFile();
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(MyService.class)
                .addClass(PlayCli.class)
                .addAsLibraries(test)
                .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }

    @Test
    public void shouldEqualsNamingProperties(){
        Assert.assertEquals(100,myService.getA());
    }

    @Test
    public void shouldNotBeEqualsNamingProperties(){
        Assert.assertNotEquals(200,myService.getA());
    }



}
