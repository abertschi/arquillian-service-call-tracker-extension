package ch.abertschi.sct.arquillian.api;

import ch.abertschi.sct.api.Configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface RecordCall
{

    boolean enabled() default true;

    String value() default "";

    Configuration.INPUT_SOURCE sourceType() default Configuration.INPUT_SOURCE.SINGLE_FILE;

    Configuration.RECORDING_MODE mode() default Configuration.RECORDING_MODE.OVERWRITE;

    boolean skipDoubles() default true;
}
