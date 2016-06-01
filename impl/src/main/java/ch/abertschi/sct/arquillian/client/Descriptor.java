package ch.abertschi.sct.arquillian.client;

import ch.abertschi.sct.arquillian.Constants;
import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.config.descriptor.api.ExtensionDef;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abertschi on 01/06/16.
 */
public class Descriptor
{
    @Inject
    @ApplicationScoped
    InstanceProducer<Descriptor> instanceProducer;

    @Inject
    Instance<ArquillianDescriptor> descriptor;

    private static Descriptor instance;

    public void init(@Observes(precedence = 200) BeforeClass before)
    {
        instance = this;
        this.instanceProducer.set(instance);
    }

    public static Descriptor get()
    {
        if (instance == null)
        {
            throw new RuntimeException("Managed dependencies are not loaded. Observers in LoadableExtension are wrong configured!");
        }
        return instance;
    }

    public Map<String, String> getProperties()
    {
        Map<String, String> props = new HashMap<>();
            /*
             * Defaults:
             */
        props.put(Constants.EXT_PROPERTY_RECORDING_ROOT, "./target/");
        props.put(Constants.EXT_PROPERTY_MOCKING_ROOT, "./");

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

}
