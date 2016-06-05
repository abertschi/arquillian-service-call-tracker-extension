package ch.abertschi.sct.arquillian.client;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

//import ch.abertschi.sct.arquillian.container.RemoteExtension;

public class ArchiveAppender implements AuxiliaryArchiveAppender
{

    @Override
    public Archive<?> createAuxiliaryArchive()
    {
        return ShrinkWrap
                .create(JavaArchive.class, "service-call-tracker-extension.jar")
                .addPackages(true, Filters.exclude(getClass().getPackage()), "ch.abertschi.sct.arquillian")
                //.addAsServiceProvider(RemoteLoadableExtension.class, RemoteExtension.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
}
