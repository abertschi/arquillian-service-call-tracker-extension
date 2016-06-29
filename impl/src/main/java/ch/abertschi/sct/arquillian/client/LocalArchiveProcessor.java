package ch.abertschi.sct.arquillian.client;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import ch.abertschi.sct.arquillian.annotation.RecordCallExtractor;
import ch.abertschi.sct.arquillian.annotation.RecordConfiguration;
import ch.abertschi.sct.arquillian.annotation.ReplayCallExtractor;
import ch.abertschi.sct.arquillian.annotation.ReplayConfiguration;
import ch.abertschi.sct.arquillian.ExtensionConfiguration;
import ch.abertschi.sct.arquillian.RecordTestConfiguration;
import ch.abertschi.sct.arquillian.ReplayTestConfiguration;
import com.sun.javaws.exceptions.InvalidArgumentException;
import com.thoughtworks.xstream.XStream;
import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.arquillian.test.spi.annotation.SuiteScoped;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.container.LibraryContainer;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.abertschi.sct.arquillian.Constants;

/**
 * Bundle all configuration into an archive to make it accessible on the server
 */
public class LocalArchiveProcessor implements ApplicationArchiveProcessor
{

    private static final String EXTENSION_JAR_NAME = "service-call-tracker-extension-resources.jar";

    private static final Logger LOG = LoggerFactory.getLogger(LocalArchiveProcessor.class);

    @Inject
    @SuiteScoped
    InstanceProducer<LocalArchiveProcessor> instanceProducer;

    @Inject
    Instance<Descriptor> descriptor;

    public void init(@Observes(precedence = 50) BeforeClass before)
    {
        this.instanceProducer.set(this);
    }

    @Override
    public void process(Archive<?> applicationArchive, TestClass testClass)
    {
        File replayingStorage = new File(new File("."), descriptor.get().getProperty(Constants.PROPERTY_REPLAYING_STORAGE_DIRECTORY));
        File recordingStorage = new File(new File("."), descriptor.get().getProperty(Constants.PROPERTY_RECORDING_STORAGE_DIRECTORY));

        if (descriptor.get().getProperty(Constants.PROPERTY_SOURCE_DIRECTORY) == null)
        {
            // todo: ignore execution instead?
            throw new IllegalArgumentException("Source Directory for service call tracker not set in arquillian.xml");
        }
        File sourceBase = new File(new File("."), descriptor.get().getProperty(Constants.PROPERTY_SOURCE_DIRECTORY));

        // read recording annotation
        // todo: read only if not disabled
        RecordCallExtractor recordExtractor = new RecordCallExtractor(sourceBase, recordingStorage);
        RecordConfiguration recordClassConfig = recordExtractor.extractClassConfiguration(testClass);
        List<RecordConfiguration> recordMethodConfigs = recordExtractor.extractMethodConfigurations(testClass);

        RecordTestConfiguration recordTestConfig = new RecordTestConfiguration()
                .setMethodConfigurations(recordMethodConfigs)
                .setClassConfiguration(recordClassConfig)
                .setOrigin(RecordTestConfiguration.createOrigin(testClass.getJavaClass()));

        // read replaying annotation
        // todo: read only if not disabled
        ReplayCallExtractor replayExtractor = new ReplayCallExtractor(sourceBase, replayingStorage);
        ReplayConfiguration replayClassConfig = replayExtractor.extractClassConfiguration(testClass);
        List<ReplayConfiguration> replayMethodConfigs = replayExtractor.extractMethodConfigurations(testClass);

        ReplayTestConfiguration replayTestConfig = new ReplayTestConfiguration()
                .setMethodConfigurations(replayMethodConfigs)
                .setClassConfiguration(replayClassConfig)
                .setOrigin(ReplayTestConfiguration.createOrigin(testClass.getJavaClass()));

        // write configuration xml to archive in order to access in container
        ExtensionConfiguration configuration = new ExtensionConfiguration()
                .setRecordConfigurations(Arrays.asList(recordTestConfig))
                .setReplayConfigurations(Arrays.asList(replayTestConfig));

        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, EXTENSION_JAR_NAME);
        jar.add(new StringAsset(configuration.toXml()), Constants.CONFIGURATION_FILE);

        System.out.println(configuration.toXml());
        if (JavaArchive.class.isInstance(applicationArchive))
        {
            applicationArchive.merge(jar);
        }
        else
        {
            final LibraryContainer<?> libraryContainer = (LibraryContainer<?>) applicationArchive;
            libraryContainer.addAsLibrary(jar);
        }
    }
}
