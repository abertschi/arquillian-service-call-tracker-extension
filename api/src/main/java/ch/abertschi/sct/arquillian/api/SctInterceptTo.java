package ch.abertschi.sct.arquillian.api;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation to specify dataset for service call recording.
 * 
 * @author Andrin Bertschi
 * 
 */
@Target({METHOD, ElementType.TYPE})
@Retention(RUNTIME)
public @interface SctInterceptTo {

    /**
     * Relative path to the requested resource (Root is project root).
     */
    public String value();

    // /**
    // * TODO: Feature not yet implemented
    // * Absolute path to dataset. If {@link #absolute()} is used {@link #value()} will be ignored.
    // */
    // public String absolute() default "";

    // /**
    // * TODO: Feature not yet implemented
    // * Override dataset, if it already exists. Default is {@code true}.
    // */
    // public boolean override() default true;

}
