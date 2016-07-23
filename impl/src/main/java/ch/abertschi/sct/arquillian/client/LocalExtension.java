package ch.abertschi.sct.arquillian.client;


import ch.abertschi.sct.arquillian.Constants;
import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.config.descriptor.api.ExtensionDef;
import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.container.test.spi.client.deployment.ProtocolArchiveProcessor;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;

import java.util.HashMap;
import java.util.Map;

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

    public static class Descriptor
    {
        @Inject
        @ApplicationScoped
        InstanceProducer<Descriptor> instanceProducer;

        @Inject
        Instance<ArquillianDescriptor> descriptor;

        private static Descriptor instance;

        private Map<String, String> properties;

        public static Descriptor get()
        {
            if (instance == null)
            {
                throw new RuntimeException("Managed dependencies are not loaded. Observers in LoadableExtension are wrong configured!");
            }
            return instance;
        }

        public boolean getBooleanProperty(String name)
        {
            String val = this.properties.get(name);
            return hasProperty(name) && val.toUpperCase().equals("TRUE");
        }

        public String getProperty(String name)
        {
            return this.properties.get(name);
        }

        public String getProperty(String name, String defaultVal)
        {
            return hasProperty(name) ? this.properties.get(name) : defaultVal;
        }

        public boolean hasProperty(String name)
        {
            String val = this.properties.get(name);
            return val != null && !val.trim().isEmpty();
        }

        public void init(@Observes(precedence = 200) BeforeClass before)
        {
            instance = this;
            this.instanceProducer.set(instance);
            this.properties = createProperties();
        }

        private Map<String, String> createProperties()
        {
            Map<String, String> props = new HashMap<>();

            // defaults
            props.put(Constants.PROPERTY_ENABLED, "true");
            props.put(Constants.PROPERTY_REPLAYING_ENABLED, "true");
            props.put(Constants.PROPERTY_RECORDING_ENABLED, "true");
            props.put(Constants.PROPERTY_REPLAYING_SKIP_DOUBLES, "false");
            props.put(Constants.PROPERTY_THROW_EXCEPTION_ON_INCOMPATIBLE_RETURN_TYPE, "true");
            props.put(Constants.PROPERTY_THROW_EXCEPTION_ON_NOT_FOUND, "true");
            props.put(Constants.PROPERTY_RECORDING_STORAGE_DIRECTORY, "./");
            props.put(Constants.PROPERTY_REPLAYING_STORAGE_DIRECTORY, "./");
            props.put(Constants.PROPERTY_SOURCE_DIRECTORY, "./src/main/java");

            for (ExtensionDef e : this.descriptor.get().getExtensions())
            {
                if (e.getExtensionName().equals(Constants.EXT_NAME))
                {
                    for (Map.Entry<String, String> entry : e.getExtensionProperties().entrySet())
                    {
                        props.put(entry.getKey(), entry.getValue());
                    }
                    break;
                }
            }
            return props;
        }

        public Map<String, String> getProperties()
        {
            return this.properties;
        }
    }
}
