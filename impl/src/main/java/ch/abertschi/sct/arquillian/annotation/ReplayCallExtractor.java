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
public class ReplayCallExtractor
{
    private final File sourceBaseDir;
    private final File storageBaseDir;

    public ReplayCallExtractor(File sourceBaseDir, File storageBaseDir)
    {
        this.storageBaseDir = storageBaseDir;
        this.sourceBaseDir = sourceBaseDir;
    }

    private File getStorageFile(File baseDir, String hint)
    {
        File storage = new File(baseDir, hint);

        System.out.println("file: " + storage.getAbsolutePath());
        if (storage.exists())
        {
            return storage;
        }
        // look for lower case only
        storage = new File(baseDir, hint.toLowerCase());
        if (storage.exists())
        {
            return storage;
        }
        // look for camel_case
        String underline = hint.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
        storage = new File(baseDir, underline);
        if (storage.exists())
        {
            return storage;
        }
        return null;
    }


    private File extractFromSourceBase(Class testClass, String hint)
    {
        String packageName = testClass.getPackage().getName().replace(".", "/");
        File baseDir = new File(this.sourceBaseDir, packageName);
        System.out.println(baseDir);
        File storage = null;
        if (hint != null && !hint.trim().isEmpty())
        {
            System.out.println(hint);
            storage = getStorageFile(baseDir, hint);
            if (storage == null && !hint.endsWith(".xml"))
            {
                storage = getStorageFile(baseDir, hint.concat(".xml"));
            }
        }
        return storage;
    }

    private File extractFromStorageBase(String hint)
    {
        File storage = null;
        if (hint != null && !hint.trim().isEmpty())
        {
            storage = getStorageFile(this.storageBaseDir, hint);
            if (storage == null && !hint.endsWith(".xml"))
            {
                storage = getStorageFile(this.storageBaseDir, hint.concat(".xml"));
            }
        }
        return storage;
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
                method -> {
                    return extractConfiguration(testClass.getJavaClass(), method, method.getAnnotation(ReplayCall.class), true);
                });
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
            return new ReplayConfiguration(targetClass, targetMethod)
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
