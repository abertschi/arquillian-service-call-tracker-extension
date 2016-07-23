package ch.abertschi.sct.arquillian.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import ch.abertschi.sct.arquillian.*;
import ch.abertschi.sct.arquillian.annotation.RecordCallExtractor;
import ch.abertschi.sct.arquillian.annotation.RecordConfiguration;
import ch.abertschi.sct.arquillian.annotation.ReplayCallExtractor;
import ch.abertschi.sct.arquillian.annotation.ReplayConfiguration;
import ch.abertschi.sct.arquillian.api.RecordCall;
import ch.abertschi.sct.arquillian.api.ReplayCall;
import com.github.underscore.$;
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
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.container.LibraryContainer;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bundle all configuration into an archive to make it accessible on the server
 */
public class LocalArchiveProcessor implements ApplicationArchiveProcessor
{

    private static final String EXTENSION_JAR_NAME = "service-call-tracker-extension-resources.jar";

    private static final Logger LOG = LoggerFactory.getLogger(LocalArchiveProcessor.class);

    private File recordingStorage;
    private File replayingStorage;
    private File sourceBase;


    @Inject
    private Instance<LocalExtension.Descriptor> descriptor;

    @Inject
    @SuiteScoped
    InstanceProducer<LocalArchiveProcessor> instanceProducer;


    public void before(@Observes(precedence = 50) BeforeClass before)
    {
        this.instanceProducer.set(this);
    }

    private void init()
    {
        this.replayingStorage = new File(new File("."), descriptor.get().getProperty(Constants.PROPERTY_REPLAYING_STORAGE_DIRECTORY));
        this.recordingStorage = new File(new File("."), descriptor.get().getProperty(Constants.PROPERTY_RECORDING_STORAGE_DIRECTORY));
        this.sourceBase = new File(new File("."), descriptor.get().getProperty(Constants.PROPERTY_SOURCE_DIRECTORY));
    }

    @Override
    public void process(Archive<?> applicationArchive, TestClass testClass)
    {
        if (!descriptor.get().getBooleanProperty(Constants.PROPERTY_ENABLED))
        {
            return;
        }
        if (descriptor.get().getProperty(Constants.PROPERTY_SOURCE_DIRECTORY) == null)
        {
            throw new IllegalArgumentException("Source Directory for service call tracker is not set in arquillian.xml");
        }
        init();
        if (descriptor.get().getBooleanProperty(Constants.PROPERTY_SUITE_EXTENSION))
        {
            List<TestClass> classes = TestClassScanner.GET.findTestClassAnnotatedBy(RecordCall.class);
            classes.addAll(TestClassScanner.GET.findTestClassAnnotatedBy(ReplayCall.class));

            List<String> added = new ArrayList<>();
            classes = $.filter(classes, c -> {
                if (added.contains(c.getName()))
                {
                    return false;
                }
                else
                {
                    added.add(c.getName());
                    return true;
                }
            });
            classes.forEach(javaClass -> processTestClass(applicationArchive, javaClass));
        }
        else
        {
            processTestClass(applicationArchive, testClass);
        }
    }

    private void processTestClass(Archive<?> applicationArchive, TestClass testClass)
    {
        ExtensionConfiguration configuration = new ExtensionConfiguration();
        if (descriptor.get().getBooleanProperty(Constants.PROPERTY_REPLAYING_ENABLED))
        {
            ReplayTestConfiguration replay = extractReplaying(testClass);
            configuration.setReplayConfigurations(Arrays.asList(replay));
            if (!$.isEmpty(configuration.getAllReplayingConfigurations()))
            {
                addReplayingFilesToArchive(applicationArchive, replay);
            }
        }
        if (descriptor.get().getBooleanProperty(Constants.PROPERTY_RECORDING_ENABLED))
        {
            configuration.setRecordConfigurations(Arrays.asList(extractRecording(testClass)));
        }

        // write configuration xml to archive in order to access in container
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, EXTENSION_JAR_NAME);
        jar.add(new StringAsset(configuration.toXml()), Constants.CONFIGURATION_FILE);
        merge(applicationArchive, jar);
    }

    private RecordTestConfiguration extractRecording(TestClass testClass)
    {
        // read recording annotation
        RecordCallExtractor recordExtractor = new RecordCallExtractor(recordingStorage);
        RecordConfiguration recordClassConfig = recordExtractor.extractClassConfiguration(testClass);
        List<RecordConfiguration> recordMethodConfigs = recordExtractor.extractMethodConfigurations(testClass);

        return new RecordTestConfiguration(testClass.getJavaClass())
                .setMethodConfigurations(recordMethodConfigs)
                .setClassConfiguration(recordClassConfig);
    }

    private ReplayTestConfiguration extractReplaying(TestClass testClass)
    {
        LOG.debug(descriptor.get().getProperty(Constants.PROPERTY_SOURCE_DIRECTORY));
        LOG.debug(sourceBase.getAbsolutePath());
        LOG.debug(replayingStorage.getAbsolutePath());

        ReplayCallExtractor replayExtractor = new ReplayCallExtractor(sourceBase, replayingStorage);
        ReplayConfiguration replayClassConfig = replayExtractor.extractClassConfiguration(testClass);
        List<ReplayConfiguration> replayMethodConfigs = replayExtractor.extractMethodConfigurations(testClass);

        return new ReplayTestConfiguration(testClass.getJavaClass())
                .setMethodConfigurations(replayMethodConfigs)
                .setClassConfiguration(replayClassConfig);
    }

    private void addReplayingFilesToArchive(Archive rootArchive, ReplayTestConfiguration config)
    {
        List<ReplayConfiguration> replayings = new ArrayList<>();
        if (!$.isEmpty(config.getMethodConfigurations()))
        {
            replayings.addAll(config.getMethodConfigurations());
        }
        if (config.getClassConfiguration() != null)
        {
            replayings.add(config.getClassConfiguration());
        }

        LOG.trace(new XStream().toXML(replayings));

        JavaArchive resources = ShrinkWrap.create(JavaArchive.class);
        $.forEach(replayings, r -> {
            LOG.trace(r.getPath());
            String archivePath = Constants.CONFIGURATION_PATH + r.getOrigin();
            resources.add(new FileAsset(new File(r.getPath())), archivePath);
            r.setPath(archivePath);
        });
        merge(rootArchive, resources);
    }

    private void merge(Archive rootArchive, Archive toAdd)
    {
        if (JavaArchive.class.isInstance(rootArchive))
        {
            rootArchive.merge(toAdd);
        }
        else
        {
            final LibraryContainer<?> libraryContainer = (LibraryContainer<?>) rootArchive;
            libraryContainer.addAsLibrary(toAdd);
        }
    }
}
