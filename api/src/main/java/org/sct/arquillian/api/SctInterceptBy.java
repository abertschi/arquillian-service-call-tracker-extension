package org.sct.arquillian.api;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation to specify dataset for response loading of an intercepted service call.
 * 
 * @author Andrin Bertschi
 * 
 */
@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface SctInterceptBy {

    /**
     * Relative path to the requested resource (Root is project root).
     */
    public String value();

    // TODO: Feature not yet implemented
    // public String absolute() default "";

}