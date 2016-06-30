package ch.abertschi.sct.arquillian.client;

import ch.abertschi.sct.arquillian.container.RemoteExtension;
import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public class ArchiveAppender implements AuxiliaryArchiveAppender
{

    @Override
    public Archive<?> createAuxiliaryArchive()
    {
        return ShrinkWrap
                .create(JavaArchive.class, "arquillian-service-call-tracker-extension.jar")
                .addPackages(true, "ch.abertschi.sct.arquillian")
                .addAsServiceProvider(RemoteLoadableExtension.class, RemoteExtension.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
}
