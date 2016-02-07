package org.sct.arquillian.deployment;

import java.io.File;

import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.container.LibraryContainer;
import org.jboss.shrinkwrap.resolver.api.ResolutionException;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import org.sct.arquillian.AsctConstants;
import org.sct.arquillian.client.AsctLocalExtension.AsctDescriptor;
import org.sct.arquillian.util.exception.AsctException;

/**
 * 
 * Appender to include necessary libraries used by extension.
 * 
 * @author Andrin Bertschi
 * 
 */
public class AsctDependencyResolver implements ApplicationArchiveProcessor {

    private static final String SERVICVE_MOCK = "org.sct:service-call-tracker-impl";

    @Override
    public void process(Archive<?> archive, TestClass testClass)
    {
        if (archive instanceof LibraryContainer)
        {
            LibraryContainer<?> container = (LibraryContainer<?>) archive;
            container.addAsLibraries(Maven.resolver().offline()
                    .loadPomFromFile("pom.xml").resolve(SERVICVE_MOCK)
                    .withClassPathResolution(true).withTransitivity().as(File.class));

            container.addAsLibraries(Maven.resolver().offline()
                    .loadPomFromFile("pom.xml").resolve("commons-io:commons-io")
                    .withClassPathResolution(true).withTransitivity().as(File.class));

            container.addAsLibraries(Maven.resolver().offline()
                    .loadPomFromFile("pom.xml").resolve("com.google.jimfs:jimfs")
                    .withClassPathResolution(true).withTransitivity().as(File.class));
        }
    }
}
