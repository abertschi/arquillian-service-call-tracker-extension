package ch.abertschi.sct.arquillian.api;

import ch.abertschi.sct.api.Configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface ReplayCall
{
    boolean enabled() default true;

    String value() default "";

    boolean throwExceptionOnNotFound() default false;

    boolean throwExceptionOnIncompatibleReturnType() default false;

    Configuration.INPUT_SOURCE sourceType() default Configuration.INPUT_SOURCE.SINGLE_FILE;
}
