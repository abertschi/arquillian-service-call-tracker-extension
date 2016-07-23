package ch.abertschi.sct.arquillian.annotation;

import ch.abertschi.sct.arquillian.api.RecordCall;
import com.github.underscore.$;
import org.jboss.arquillian.test.spi.TestClass;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class RecordCallExtractor
{
    private File storageBaseDir;

    public RecordCallExtractor(File storageBaseDir)
    {
        this.storageBaseDir = storageBaseDir;
    }

    public RecordConfiguration extractClassConfiguration(TestClass testClass)
    {
        return testClass.isAnnotationPresent(RecordCall.class) ? extractConfiguration(testClass.getJavaClass(), null,
                testClass.getAnnotation(RecordCall.class)) : null;
    }

    public List<RecordConfiguration> extractMethodConfigurations(TestClass testClass)
    {
        return $.map(Arrays.asList(testClass.getMethods(RecordCall.class)),
                method -> extractConfiguration(testClass.getJavaClass(), method, method.getAnnotation(RecordCall.class)));
    }

    // returns null if nothing can be extracted
    private RecordConfiguration extractConfiguration(Class<?> targetClass, Method targetMethod, RecordCall annotation)
    {
        String hint = annotation.value();
        if (hint == null || hint.isEmpty())
        {
            hint = targetMethod != null ? targetMethod.getName() : targetClass.getName();
        }
        if (!hint.endsWith(".xml"))
        {
            hint = hint.concat(".xml");
        }
        File file = new File(this.storageBaseDir, hint);

        return new RecordConfiguration(targetClass, targetMethod)
                .setEnabled(annotation.enabled())
                .setPath(file.getAbsolutePath())
                .setSourceType(annotation.sourceType())
                //.setSkipDoubles(annotation.skipDoubles())
                .setMode(annotation.mode());
    }
}
