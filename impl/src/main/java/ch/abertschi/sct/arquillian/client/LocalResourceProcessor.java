package ch.abertschi.sct.arquillian.client;

import java.util.ArrayList;
import java.util.List;

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
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.abertschi.sct.arquillian.Constants;
import ch.abertschi.sct.arquillian.api.SctInterceptBy;
import ch.abertschi.sct.arquillian.api.SctInterceptTo;
import ch.abertschi.sct.arquillian.bootstrap.TestClassScanner;
import ch.abertschi.sct.arquillian.client.AsctLocalExtension.AsctDescriptor;
import ch.abertschi.sct.arquillian.resource.ResourcePackager;
import ch.abertschi.sct.arquillian.resource.extraction.AnnotationExtractors;
import ch.abertschi.sct.arquillian.resource.index.ResourceIndexBuilderImpl;
import ch.abertschi.sct.arquillian.resource.model.Resource;

/**
 * Bundle all configuration into an archive to make it accessible on the server
 */
public class LocalResourceProcessor implements ApplicationArchiveProcessor
{

    private static final String EXTENSION_JAR_NAME = "service-call-tracker-extension-resources.jar";

    private static final Logger LOG = LoggerFactory.getLogger(LocalResourceProcessor.class);

    @Inject
    @SuiteScoped
    InstanceProducer<LocalResourceProcessor> instanceProducer;

    @Inject
    Instance<AsctDescriptor> descriptor;

    public void init(@Observes(precedence = 50) BeforeClass before)
    {
        this.instanceProducer.set(this);
    }

    @Override
    public void process(Archive<?> applicationArchive, TestClass testClass)
    {
        List<Resource> recordingResources = new ArrayList<>();
        final AnnotationExtractors extraction = new AnnotationExtractors(this.descriptor.get());
        for (TestClass clazz : TestClassScanner.GET.findTestClassAnnotatedBy(SctInterceptTo.class))
        {
            List<Resource> r = extraction.extractRecordingResources(clazz);
            if (r != null)
            {
                recordingResources.addAll(r);
            }
        }
        List<Resource> mockResources = new ArrayList<>();
        for (TestClass clazz : TestClassScanner.GET.findTestClassAnnotatedBy(SctInterceptBy.class))
        {
            List<Resource> r = extraction.extractMockingResources(clazz);
            if (r != null)
            {
                mockResources.addAll(r);
            }
        }

        final ResourcePackager packager = new ResourcePackager();
        mockResources = packager.moveResources(Constants.RESOURCE_ROOT, mockResources);

        Resource mockIndex = new ResourceIndexBuilderImpl().
                createIndex(Constants.RESOURCE_MOCKING_INDEX, mockResources);
        Resource recordIndex = new ResourceIndexBuilderImpl().
                createIndex(Constants.RESOURCE_RECORDING_INDEX, recordingResources);

        JavaArchive resourcesJar = ShrinkWrap.create(JavaArchive.class, EXTENSION_JAR_NAME);
        packager.addResourcesToArchive(resourcesJar, mockResources);
        packager.addResourceToArchive(resourcesJar, recordIndex);
        packager.addResourceToArchive(resourcesJar, mockIndex);

        packager.mergeArchives(applicationArchive, resourcesJar);
    }
}
