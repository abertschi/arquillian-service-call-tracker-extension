package ch.abertschi.sct.arquillian.deployment;

import org.jboss.arquillian.container.test.spi.TestDeployment;
import org.jboss.arquillian.container.test.spi.client.deployment.ProtocolArchiveProcessor;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.container.LibraryContainer;

/**
 * Appender to include server side libraries used by this extension.
 *
 * @author Andrin Bertschi
 */
public class AsctDependencyResolver implements ProtocolArchiveProcessor
{
    private static final String SCT_IMPL = "ch.abertschi.sct:service-call-tracker-impl:0.0.1-SNAPSHOT";

    private static final String COMMONS_IO = "commons-io:commons-io:2.4";

    @Override
    public void process(TestDeployment testDeployment, Archive<?> archive)
    {
        if (archive instanceof LibraryContainer)
        {
            LibraryContainer<?> container = (LibraryContainer<?>) archive;
            container.addAsLibraries(ResolverUtil.get()
                    .resolve(SCT_IMPL, COMMONS_IO).withTransitivity().asFile());
        }
    }
}
