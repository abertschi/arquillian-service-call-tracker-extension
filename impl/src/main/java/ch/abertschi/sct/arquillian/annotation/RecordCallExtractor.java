package ch.abertschi.sct.arquillian.annotation;

import ch.abertschi.sct.arquillian.api.RecordCall;
import com.github.underscore.$;
import org.jboss.arquillian.test.spi.TestClass;

import java.io.File;
import java.lang.reflect.Method;
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
        if (testClass.isAnnotationPresent(RecordCall.class))
        {
            RecordCall annotation = testClass.getAnnotation(RecordCall.class);
            String hint;
            if (annotation.value() != null && !annotation.value().isEmpty())
            {
                hint = annotation.value();
            }
            else
            {
                hint = testClass.getName();
            }

            File storage = extractFromSourceBase(testClass.getJavaClass(), hint);
            if (storage == null)
            {
                storage = extractFromStorageBase(hint);
            }
            if (storage != null)
            {
                return new RecordConfiguration()
                        .setName(testClass.getName())
                        .setEnabled(annotation.enabled())
                        .setPath(storage.getAbsolutePath())
                        .setSourceType(annotation.sourceType())
                        .setSkipDoubles(annotation.skipDoubles())
                        .setMode(annotation.mode());
            }
        }
        return null;
    }

    public List<RecordConfiguration> extractMethodConfigurations(TestClass testClass)
    {
        return $.map(Arrays.asList(testClass.getMethods(RecordCall.class)),
                method -> extractMethodConfiguration(testClass.getJavaClass(), method, method.getAnnotation(RecordCall.class)));
    }

    // returns null if nothing can be extracted
    private RecordConfiguration extractMethodConfiguration(Class<?> targetClass, Method method, RecordCall annotation)
    {
        File storage;
        String hint;
        if (annotation.value() != null && !annotation.value().isEmpty())
        {
            hint = annotation.value();
        }
        else
        {
            hint = method.getName();
        }

        storage = extractFromSourceBase(targetClass, hint);
        if (storage == null)
        {
            storage = extractFromStorageBase(hint);
        }
        if (storage != null)
        {
            return new RecordConfiguration()
                    .setName(targetClass.getName()) // todo:
                    .setEnabled(annotation.enabled())
                    .setPath(storage.getAbsolutePath())
                    .setSourceType(annotation.sourceType())
                    .setSkipDoubles(annotation.skipDoubles())
                    .setMode(annotation.mode());
        }
        return null;
    }
}
