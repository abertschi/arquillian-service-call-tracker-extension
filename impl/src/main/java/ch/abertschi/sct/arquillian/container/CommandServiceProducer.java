package ch.abertschi.sct.arquillian.container;

import org.jboss.arquillian.container.test.spi.command.CommandService;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.ServiceLoader;
import org.jboss.arquillian.test.spi.annotation.SuiteScoped;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;

/**
 * Created by abertschi on 07/02/16.
 */
public class CommandServiceProducer
{
    @Inject
    private Instance<ServiceLoader> serviceLoaderInstance;

    @Inject
    @ApplicationScoped
    InstanceProducer<CommandService> commandServiceProducer;

    public void createCommandService(@Observes(precedence = 100) BeforeSuite beforeSuite)
    {
        final ServiceLoader serviceLoader = serviceLoaderInstance.get();
        if (serviceLoader == null)
        {
            throw new IllegalStateException("No " + ServiceLoader.class.getName() + " found in context");
        }

        final CommandService commandService = serviceLoader.onlyOne(CommandService.class);
        if (commandService == null)
        {
            throw new IllegalStateException("No " + CommandService.class.getName() + " found in context");
        }

        commandServiceProducer.set(commandService);
    }
}
