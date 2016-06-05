package ch.abertschi.sct.arquillian.client;


import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.container.test.spi.client.deployment.ProtocolArchiveProcessor;
import org.jboss.arquillian.core.spi.LoadableExtension;

public class LocalExtension implements LoadableExtension
{
    @Override
    public void register(ExtensionBuilder builder)
    {
        builder.service(ProtocolArchiveProcessor.class, DependencyResolver.class)
                .service(AuxiliaryArchiveAppender.class, ArchiveAppender.class)
                .service(ApplicationArchiveProcessor.class, LocalArchiveProcessor.class)
                .observer(ResourceCommandReceiver.class)
                .observer(Descriptor.class);
    }
}
