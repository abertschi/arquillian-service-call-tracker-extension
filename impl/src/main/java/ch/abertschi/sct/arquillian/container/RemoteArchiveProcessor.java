package ch.abertschi.sct.arquillian.container;

import java.io.File;
import java.io.IOException;

import ch.abertschi.sct.arquillian.ExtensionConfiguration;
import ch.abertschi.sct.arquillian.RecordTestConfiguration;
import ch.abertschi.sct.arquillian.annotation.RecordConfiguration;
import org.apache.commons.io.IOUtils;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.abertschi.sct.arquillian.Constants;

public class RemoteArchiveProcessor
{

    private static final Logger LOG = LoggerFactory.getLogger(RemoteArchiveProcessor.class);

    @Inject
    @ApplicationScoped
    private InstanceProducer<RemoteConfiguration> remoteConfigurationProducer;

    public void init(@Observes BeforeSuite suite) throws IOException
    {
        RemoteConfiguration remoteConfig = new RemoteConfiguration();
        ExtensionConfiguration config = loadConfiguration();
        prepareRecordingFiles(config);
        remoteConfig.setConfiguration(config);
        remoteConfigurationProducer.set(remoteConfig);
    }

    private ExtensionConfiguration loadConfiguration()
    {
        ExtensionConfiguration config = null;
        try
        {
            String xml = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResource(Constants.CONFIGURATION_FILE).openStream());
            if (xml != null || !xml.isEmpty())
            {
                config = ExtensionConfiguration.fromXml(xml);
            }
        }
        catch (IOException e)
        {
            // treat any io exception as if file was empty
        }
        return config;
    }

    private void prepareRecordingFiles(ExtensionConfiguration config) throws IOException
    {
        for (RecordConfiguration recording : config.getAllRecordConfigurations())
        {
            recording.setContainerPath(File.createTempFile(recording.getOrigin(), "").getAbsolutePath());
            LOG.info("Temp. recording file created {}", recording.getContainerPath());
        }
    }
}
