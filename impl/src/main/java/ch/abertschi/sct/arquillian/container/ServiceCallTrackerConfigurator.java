package ch.abertschi.sct.arquillian.container;

import ch.abertschi.sct.api.Configuration;
import ch.abertschi.sct.api.SctConfigurator;
import ch.abertschi.sct.api.ServiceCallTrackerFactory;
import ch.abertschi.sct.arquillian.RecordTestConfiguration;
import ch.abertschi.sct.arquillian.ResourceCommand;
import ch.abertschi.sct.arquillian.annotation.RecordConfiguration;
import ch.abertschi.sct.arquillian.annotation.ReplayConfiguration;
import com.github.underscore.$;
import com.thoughtworks.xstream.XStream;
import org.jboss.arquillian.container.test.spi.command.CommandService;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.After;
import org.jboss.arquillian.test.spi.event.suite.AfterSuite;
import org.jboss.arquillian.test.spi.event.suite.Before;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by abertschi on 30/06/16.
 */
public class ServiceCallTrackerConfigurator
{
    private static final Logger LOG = LoggerFactory.getLogger(RemoteArchiveProcessor.class);

    @Inject
    private Instance<RemoteConfiguration> configuration;

    @Inject
    private Instance<CommandService> commandService;

    public void init(@Observes BeforeSuite before)
    {
    }

    public void beforeTest(@Observes Before before)
    {
        Configuration config = new Configuration();
        SctConfigurator.getInstance().setGlobalConfiguration(config);

        RecordConfiguration recording = configuration.get().getConfiguration()
                .getRecordConfiguration(before.getTestClass().getJavaClass(), before.getTestMethod());

        if (recording != null)
        {
            config.setRecordingSourceType(recording.getSourceType())
                    .setRecordingMode(recording.getMode())
                    .setRecordingSkipDoubles(recording.isSkipDoubles())
                    .setRecordingSource(new File(recording.getContainerPath()))
                    .setRecordingEnabled(recording.isEnabled());
        }

        ReplayConfiguration replaying = configuration.get().getConfiguration()
                .getReplayConfiguration(before.getTestClass().getJavaClass(), before.getTestMethod());

        if (replaying != null)
        {
            config.setReplayingEnabled(replaying.isEnabled())
                    .setReplayingSourceType(replaying.getSourceType())
                    .setReplayingSource(Thread.currentThread().getContextClassLoader().getResource(replaying.getPath()))
                    .setThrowExceptionOnIncompatibleReturnType(replaying.isThrowExceptionOnIncompatibleReturnType())
                    .setThrowExceptionOnNotFound(replaying.isThrowExceptionOnNotFound());
        }
    }

    public void afterTest(@Observes AfterSuite after)
    {
        for (RecordConfiguration r : configuration.get().getConfiguration().getAllRecordConfigurations())
        {
            String content = readFile(new File(r.getContainerPath()));
            commandService.get().execute(new ResourceCommand(r.getOrigin(), r.getPath(), content));
        }
    }

    private String readFile(File f)
    {
        String result = "";
        try
        {
            Scanner scanner = new Scanner(f).useDelimiter("\\A");
            result = scanner.hasNext() ? scanner.next() : "";
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
        }
        return result;
    }
}
