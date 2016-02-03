package org.sct.arquillian.resource.extraction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.jboss.arquillian.test.spi.TestClass;

import org.sct.arquillian.resource.model.Resource;

/**
 * 
 * @author Andrin Bertschi
 */
public abstract class AbstractAnnotationExtractor<ANNOT extends Annotation, RES extends Resource> {
    
    private final Class<ANNOT> annotationType;
    
    private final Class<RES> resourceType;
    
    protected AbstractAnnotationExtractor(Class<ANNOT> annotation, Class<RES> resource) {
        this.annotationType = annotation;
        this.resourceType = resource;
    }
        
    public boolean isCapableForType(Class<? extends Annotation> annot) {
        return this.annotationType.isAssignableFrom(annot);
    }
    
    public Class<ANNOT> getAnnotationType() {
        return annotationType;
    }
    
    public Class<RES> getResourceType() {
        return resourceType;
    }
    
    public abstract RES extractAsResource(TestClass testClass, Method testMethod, Annotation annotation);
    
}
