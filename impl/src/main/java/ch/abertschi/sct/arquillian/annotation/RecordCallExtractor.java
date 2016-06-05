package ch.abertschi.sct.arquillian.annotation;

import ch.abertschi.sct.arquillian.api.RecordCall;
import com.github.underscore.$;
import org.jboss.arquillian.test.spi.TestClass;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by abertschi on 01/06/16.
 */
public class RecordCallExtractor extends AbstractCallExtractor
{
    public RecordCallExtractor(File sourceBaseDir, File storageBaseDir)
    {
        super(sourceBaseDir, storageBaseDir);
    }

    public RecordConfiguration extractClassConfiguration(TestClass testClass)
    {
        return testClass.isAnnotationPresent(RecordCall.class) ? extractConfiguration(testClass.getJavaClass(),
                testClass.getAnnotation(RecordCall.class)) : null;
    }

    public List<RecordConfiguration> extractMethodConfigurations(TestClass testClass)
    {
        return $.map(Arrays.asList(testClass.getMethods(RecordCall.class)),
                method -> extractConfiguration(method.getClass(), method.getAnnotation(RecordCall.class), true));
    }

    private RecordConfiguration extractConfiguration(Class<?> targetClass, RecordCall annotation)
    {
        return extractConfiguration(targetClass, annotation, false);
    }

    // returns null if nothing can be extracted
    private RecordConfiguration extractConfiguration(Class<?> targetClass, RecordCall annotation, boolean throwExceptionOnNotFound)
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
            return new RecordConfiguration()
                    .setName(targetClass.getName())
                    .setEnabled(annotation.enabled())
                    .setPath(storage.getAbsolutePath())
                    .setSourceType(annotation.sourceType())
                    .setSkipDoubles(annotation.skipDoubles())
                    .setMode(annotation.mode());
        }
        return null;
    }
}
