package org.sct.arquillian.deployment;

import java.lang.reflect.Field;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.core.api.Injector;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.spi.Manager;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import org.sct.arquillian.client.AsctLocalExtension;
import org.sct.arquillian.container.AsctRemoteExtension;

/**
 * Append extension to deployable archive.
 * 
 * @author Andrin Bertschi
 */
public class AsctArchiveAppender implements AuxiliaryArchiveAppender {

    @Override
    public Archive<?> createAuxiliaryArchive() {
    	bootLocalObservers();
        return createArchive();
    }

    private Archive<?> createArchive() {
        JavaArchive jar = ShrinkWrap
                .create(JavaArchive.class, "service-call-tracker-extension.jar")

                // Add all required Asct code
                .addPackages(true,
                		// Filters.exclude(AsctArchiveAppender.class.getPackage()),        // exclude deployment package
                        "org.sct.arquillian")

                .addAsServiceProvider(RemoteLoadableExtension.class, AsctRemoteExtension.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return jar;
    }
    
    // ----------------------------------------------------------------------------------------
    // FIXME: asct-suite-extension-capable
    // ----------------------------------------------------------------------------------------

    @Inject
    private Instance<Injector> injector;

    @Inject
    @ApplicationScoped
    private InstanceProducer<AsctLocalExtension.AsctDescriptor> instanceProducer;

    /**
     * Due to conflict described in {@code asct-suite-extension-capable}, all client side observers
     * must be started manually.
     */
    public void bootLocalObservers() {
        Manager manager = getManager(injector.get());

        AsctLocalExtension.AsctDescriptor asctDescriptor = injector.get().inject(new AsctLocalExtension.AsctDescriptor());
        asctDescriptor.init(null);

        manager.bind(ApplicationScoped.class, AsctLocalExtension.AsctDescriptor.class, asctDescriptor);
    }

    private Manager getManager(Injector inj) {
        try {
            Field f = inj.getClass().getDeclaredField("manager");
            f.setAccessible(true);
            return (Manager) f.get(inj);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Something went wrong. No manager was found in context", e);
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
