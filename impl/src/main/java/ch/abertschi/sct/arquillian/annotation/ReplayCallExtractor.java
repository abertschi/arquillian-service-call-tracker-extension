package ch.abertschi.sct.arquillian.annotation;

import ch.abertschi.sct.arquillian.api.ReplayCall;
import com.github.underscore.$;
import com.thoughtworks.xstream.XStream;
import org.jboss.arquillian.test.spi.TestClass;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

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
        System.out.println(new XStream().toXML(testClass));
        return testClass.isAnnotationPresent(ReplayCall.class) ? extractConfiguration(testClass.getJavaClass(),
                testClass.getAnnotation(ReplayCall.class)) : null;
    }

    public List<ReplayConfiguration> extractMethodConfigurations(TestClass testClass)
    {
        return $.map(Arrays.asList(testClass.getMethods(ReplayCall.class)),
                method -> extractConfiguration(testClass.getJavaClass(), method, method.getAnnotation(ReplayCall.class), true));
    }

    private ReplayConfiguration extractConfiguration(Class<?> targetClass, ReplayCall annotation)
    {
        return extractConfiguration(targetClass, null, annotation, false);
    }

    // returns null if nothing can be extracted
    private ReplayConfiguration extractConfiguration(Class<?> targetClass, Method targetMethod, ReplayCall annotation, boolean throwExceptionOnNotFound)
    {
        String hint = annotation.value();
        File storage;
        hint = hint != null ? hint : targetMethod != null ? targetMethod.getName() : targetClass.getName();

        System.out.println(targetClass);

        storage = extractFromSourceBase(targetClass, hint);
        if (storage == null)
        {
            storage = extractFromStorageBase(hint);
        }
        if (storage != null)
        {
            return new ReplayConfiguration()
                    .setName(hint)
                    .setEnabled(annotation.enabled())
                    .setPath(storage.getAbsolutePath())
                    .setSourceType(annotation.sourceType())
                    .setThrowExceptionOnIncompatibleReturnType(annotation.throwExceptionOnIncompatibleReturnType())
                    .setThrowExceptionOnNotFound(annotation.throwExceptionOnNotFound());
        }

        if (throwExceptionOnNotFound)
        {
            String msg = String.format("Can not locate %s defined with %s in class %s#%s.", hint, annotation.annotationType().getSimpleName()
                    , targetClass.getName(), targetMethod != null ? targetMethod.getName() : "");
            throw new RuntimeException(msg);
        }
        else
        {
            return null;
        }
    }
}
