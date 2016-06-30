package ch.abertschi.sct.arquillian.client;

import ch.abertschi.sct.arquillian.resolve.ResolverUtil;
import org.jboss.arquillian.container.test.spi.TestDeployment;
import org.jboss.arquillian.container.test.spi.client.deployment.ProtocolArchiveProcessor;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.container.LibraryContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class DependencyResolver implements ProtocolArchiveProcessor
{
    private static final Logger LOG = LoggerFactory.getLogger(DependencyResolver.class);

    private static final String SCT_IMPL = "ch.abertschi.sct:service-call-tracker-impl:" + "0.1.0-alpha7";

    private static final String COMMONS_IO = "commons-io:commons-io:2.4";

    @Override
    public void process(TestDeployment testDeployment, Archive<?> archive)
    {
        if (archive instanceof LibraryContainer)
        {
            LibraryContainer<?> container = (LibraryContainer<?>) archive;
            for (File f : ResolverUtil.get()
                    .resolve(SCT_IMPL, COMMONS_IO).withTransitivity().asFile())
            {
                LOG.info("arquillian service call tracker adding extension dependency to archive " + f.getAbsolutePath());
                container.addAsLibraries(f);
            }
        }
    }
}
