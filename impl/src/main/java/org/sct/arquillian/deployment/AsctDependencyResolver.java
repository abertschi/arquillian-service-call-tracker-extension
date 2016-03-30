package org.sct.arquillian.deployment;

import java.io.File;

import org.jboss.arquillian.container.test.spi.TestDeployment;
import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveProcessor;
import org.jboss.arquillian.container.test.spi.client.deployment.ProtocolArchiveProcessor;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.container.LibraryContainer;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.ResolutionException;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.sct.arquillian.AsctConstants;
import org.sct.arquillian.client.AsctLocalExtension.AsctDescriptor;
import org.sct.arquillian.util.exception.AsctException;

/**
 * Appender to include necessary libraries used by extension.
 *
 * @author Andrin Bertschi
 */
public class AsctDependencyResolver implements ProtocolArchiveProcessor
{

    @Override
    public void process(TestDeployment testDeployment, Archive<?> archive)
    {
        if (archive instanceof LibraryContainer)
        {
            String settingsXml = AsctDescriptor.get().getProperties().get(AsctConstants.SETTINGS_XML);
            PomEquippedResolveStage resolver;
            if (settingsXml != null)
            {
                resolver = Maven
                        .configureResolver()
                        .fromFile(settingsXml)
                        .offline()
                        .loadPomFromFile("pom.xml");
            }
            else
            {
                resolver = Maven
                        .configureResolver()
                        .offline()
                        .loadPomFromFile("pom.xml");
            }

            LibraryContainer<?> container = (LibraryContainer<?>) archive;
            container.addAsLibraries(resolver
                    .resolve("org.sct:service-call-tracker-impl", "commons-io:commons-io")
                    .withTransitivity().as(File.class));

        }
    }
}
