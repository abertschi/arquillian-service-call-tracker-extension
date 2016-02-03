package org.sct.arquillian.client;

import java.util.HashMap;
import java.util.Map;

import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.config.descriptor.api.ExtensionDef;
import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;
import org.sct.arquillian.AsctConstants;
import org.sct.arquillian.deployment.AsctDependencyResolver;
import org.sct.arquillian.util.NotManagedByContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.sct.arquillian.deployment.AsctArchiveAppender;
import org.sct.arquillian.util.exception.AsctException;

/**
 * Extension loader for client side operations.
 * 
 * @author Andrin Bertschi
 */
public class AsctLocalExtension implements LoadableExtension {

	private static final Logger LOG = LoggerFactory.getLogger(AsctLocalExtension.class);

	@Override
	public void register(ExtensionBuilder builder) {
		LOG.debug("Registering Asct client loadable extensions");

		registerBeans(builder);
		registerServices(builder);

		LOG.debug("Asct client loadable extensions registered");
	}

	private void registerServices(ExtensionBuilder builder) {
		builder
			.service(ApplicationArchiveProcessor.class, AsctDependencyResolver.class)
			.service(AuxiliaryArchiveAppender.class, AsctArchiveAppender.class)
			.service(ApplicationArchiveProcessor.class, LocalResourceProcessor.class);
	}

	private void registerBeans(ExtensionBuilder builder) {
		builder.observer(AsctDescriptor.class);
	}

	/**
	 * Descriptor to obtain extension specific configurations such as settings specified in
	 * {@code arquillian.xml}.
	 */
	public static class AsctDescriptor {

	    @Inject
	    @ApplicationScoped
	    private InstanceProducer<AsctDescriptor> instanceProducer;

	    @Inject
	    private Instance<ArquillianDescriptor> descriptor;

	    private static AsctDescriptor instance;

	    public AsctDescriptor() {}

	    @NotManagedByContainer
	    public AsctDescriptor(InstanceProducer<AsctDescriptor> instanceProducer, Instance<ArquillianDescriptor> descriptor) {

	        this.instanceProducer = instanceProducer;
	        this.descriptor = descriptor;
	    }

	    public void init(@Observes(precedence = 200) BeforeClass before) {
	        instance = this;
	        this.instanceProducer.set(instance);
	    }

	    public static AsctDescriptor get() {
	        if (instance == null) {
	            throw new AsctException(
	                    "Managed dependencies are not loaded. Observers in LoadableExtension are wrong configured!");
	        }
	        return instance;
	    }

	    public Map<String, String> getProperties() {
	        Map<String, String> props = new HashMap<>();
	        for (ExtensionDef e : this.descriptor.get().getExtensions()) {
	            if (e.getExtensionName().equals(AsctConstants.EXT_NAME)) {
	                props = e.getExtensionProperties();
	                break;
	            }
	        }
	        return props;
	    }

	}
}
