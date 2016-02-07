package org.sct.arquillian.resource.extraction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jboss.arquillian.test.spi.TestClass;

import org.sct.arquillian.client.AsctLocalExtension.AsctDescriptor;
import org.sct.arquillian.resource.model.Resource;

/**
 * 
 * @author C925004
 * 
 */
public class AnnotationExtractors {
    
    private AsctDescriptor descriptor;

    public AnnotationExtractors(AsctDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public List<Resource> extractRecordingResources(TestClass clazz) {
        return extract(clazz, new SctInterceptToExtractor(descriptor));
    }
    
    public List<Resource> extractMockingResources(TestClass clazz) {
        return extract(clazz, new SctInterceptByExtractor(descriptor));
    }
    
    public List<Resource> extract(TestClass testClass, AbstractAnnotationExtractor<?, ?> extractor) {
        List<Resource> resources = new ArrayList<>();
            for (Method testMethod : testClass.getMethods(extractor.getAnnotationType())) {
                Annotation annotaiton = testMethod.getAnnotation(extractor.getAnnotationType());
                Resource resource = extractor.extractAsResource(testClass, testMethod, annotaiton);
                resources.add(resource);
                System.out.println("resource " + resource);
            }
        return resources;
    }
}
