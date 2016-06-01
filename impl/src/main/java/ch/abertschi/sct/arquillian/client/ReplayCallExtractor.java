package ch.abertschi.sct.arquillian.client;

import ch.abertschi.sct.arquillian.api.RecordCall;
import ch.abertschi.sct.arquillian.api.ReplayCall;
import ch.abertschi.sct.arquillian.resource.model.Resource;
import com.github.underscore.$;
import org.jboss.arquillian.test.spi.TestClass;

import java.io.File;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by abertschi on 01/06/16.
 */
public class ReplayCallExtractor extends AbstractCallExtractor
{
    public ReplayCallExtractor(File sourceBaseDir, File storageBaseDir)
    {
        super(sourceBaseDir, storageBaseDir);
    }

    public ReplayConfiguration extractClassConfiguration(TestClass testClass)
    {
        return testClass.isAnnotationPresent(ReplayCall.class) ? extractConfiguration(testClass.getJavaClass(),
                testClass.getAnnotation(ReplayCall.class)) : null;
    }

    public List<ReplayConfiguration> extractMethodConfigurations(TestClass testClass)
    {
        return $.map(Arrays.asList(testClass.getMethods(ReplayCall.class)),
                method -> extractConfiguration(method.getClass(), method.getAnnotation(ReplayCall.class), true));
    }

    private ReplayConfiguration extractConfiguration(Class<?> targetClass, ReplayCall annotation)
    {
        return extractConfiguration(targetClass, annotation, false);
    }

    // returns null if nothing can be extracted
    private ReplayConfiguration extractConfiguration(Class<?> targetClass, ReplayCall annotation, boolean throwExceptionOnNotFound)
    {
        String hint = annotation.value();
        File storage;
        hint = hint != null ? hint : targetClass.getName();

        storage = extractFromSourceBase(targetClass, hint);
        if (storage == null)
        {
            storage = extractFromStorageBase(hint);
        }
        if (storage != null)
        {
            return new ReplayConfiguration()
                    .setName(targetClass.getName())
                    .setEnabled(annotation.enabled())
                    .setPath(storage.getAbsolutePath())
                    .setSourceType(annotation.sourceType())
                    .setThrowExceptionOnIncompatibleReturnType(annotation.throwExceptionOnIncompatibleReturnType())
                    .setThrowExceptionOnNotFound(annotation.throwExceptionOnNotFound());
        }

        if (throwExceptionOnNotFound)
        {
            String msg = String.format("Can not look up %s of class %s with value %s.", annotation.getClass().getSimpleName()
                    , targetClass.getName(), hint);
            throw new RuntimeException(msg);
        }
        else
        {
            return null;
        }
    }
}
