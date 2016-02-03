package org.sct.arquillian.client;

import java.lang.annotation.Annotation;
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

import org.sct.arquillian.AsctConstants;
import org.sct.arquillian.api.SctInterceptBy;
import org.sct.arquillian.api.SctInterceptTo;
import org.sct.arquillian.bootstrap.TestClassScanner;
import org.sct.arquillian.client.AsctLocalExtension.AsctDescriptor;
import org.sct.arquillian.resource.ResourcePackager;
import org.sct.arquillian.resource.extraction.AnnotationExtractors;
import org.sct.arquillian.resource.index.ResourceIndexBuilderImpl;
import org.sct.arquillian.resource.model.Resource;
import org.sct.arquillian.util.NotManagedByContainer;

/**
 * 
 * Processor which extracts and packages all resources to the archive.
 * 
 * @author Andrin Bertschi
 * 
 */
public class LocalResourceProcessor implements ApplicationArchiveProcessor {

    private static final String EXTENSION_JAR_NAME = "service-call-tracker-extension-resources.jar";
    
    private static final Logger LOG = LoggerFactory.getLogger(LocalResourceProcessor.class);

    @Inject
    @SuiteScoped
    private InstanceProducer<LocalResourceProcessor> instanceProducer;

    @Inject
    private Instance<AsctDescriptor> descriptor;

    LocalResourceProcessor() {
    }

    public LocalResourceProcessor(InstanceProducer<LocalResourceProcessor> instanceProducer,  Instance<AsctDescriptor> descriptor) {
        this.instanceProducer = instanceProducer;
        this.descriptor = descriptor;
    }

    public void init(@Observes(precedence = 50) BeforeClass before) {
        // TODO: Try to automate this boiler code by using Arquillian Injector
        this.instanceProducer.set(this);
    }

    @Override
    public void process(Archive<?> applicationArchive, TestClass testClass) {
        // TODO: scan for annotations if run with suitextension
        List<Resource> mockResources = scanForAnnotation(SctInterceptBy.class);
        List<Resource> recordingResources = scanForAnnotation(SctInterceptTo.class);

        final ResourcePackager packager = new ResourcePackager();
        mockResources = packager.moveResources(AsctConstants.RESOURCE_ROOT, mockResources);

        Resource mockIndex = new ResourceIndexBuilderImpl().
                createIndex(AsctConstants.RESOURCE_MOCKING_INDEX, mockResources);
        Resource recordIndex = new ResourceIndexBuilderImpl().
                createIndex(AsctConstants.RESOURCE_RECORDING_INDEX, recordingResources);

        JavaArchive resourcesJar = ShrinkWrap.create(JavaArchive.class, EXTENSION_JAR_NAME);
        packager.addResourcesToArchive(resourcesJar, mockResources);
        packager.addResourceToArchive(resourcesJar, recordIndex);
        packager.addResourceToArchive(resourcesJar, mockIndex);

        packager.mergeArchives(applicationArchive, resourcesJar);
    }

    List<Resource> scanForAnnotation(Class<? extends Annotation> testAnnotation) {
        List<TestClass> testClasses = TestClassScanner.GET.findTestClassAnnotatedBy(testAnnotation);
        List<Resource> resources = new ArrayList<>();

        AnnotationExtractors extraction = new AnnotationExtractors(this.descriptor.get());
        for (TestClass clazz : testClasses) {
            List<Resource> res = extraction.extractMockingResources(clazz);
            if (res != null) {
                resources.addAll(res);
            }
        }
        LOG.info("{} resources found specified by {}", new Object[]{resources.size(), testAnnotation.getName()});
        return resources;
    }
}
