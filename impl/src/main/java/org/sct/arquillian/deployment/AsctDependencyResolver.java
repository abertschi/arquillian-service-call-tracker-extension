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

    private static final String SERVICVE_MOCK = "org.sct:service-call-tracker-base-impl";

    @Inject
    private Instance<AsctDescriptor> descriptor;
    
    public AsctDependencyResolver() {
    }
    
    public AsctDependencyResolver(Instance<AsctDescriptor> descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public void process(Archive<?> applicationArchive, TestClass testClass) {
        appendServiceMock(applicationArchive);
    }

    private void append(Archive<?> archive, String artifact) {
        if (archive instanceof LibraryContainer) {
            LibraryContainer<?> container = (LibraryContainer<?>) archive;
            
            System.out.println(descriptor.get().getProperties());
                
            File[] files = Maven.configureResolver()
                    .fromFile(descriptor.get().getProperties().get(AsctConstants.EXT_PROPERTY_MAVEN_SETTINGS_XML))
                    .offline().loadPomFromFile("pom.xml").resolve(artifact).withMavenCentralRepo(false)
                    .withTransitivity().asFile();
            container.addAsLibraries(files);
        }
    }

    private void appendServiceMock(Archive<?> archive) {
        try {
            append(archive, SERVICVE_MOCK);
        } catch (ResolutionException e) {
            throw new AsctException(String.format("Dependency %s is not definded in pom.xml.", SERVICVE_MOCK));
        }
    }
}
