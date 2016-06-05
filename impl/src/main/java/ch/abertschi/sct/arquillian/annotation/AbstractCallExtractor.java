package ch.abertschi.sct.arquillian.annotation;

import ch.abertschi.sct.arquillian.api.ReplayCall;
import com.github.underscore.$;
import org.jboss.arquillian.test.spi.TestClass;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

/**
 * Created by abertschi on 01/06/16.
 */
public abstract class AbstractCallExtractor
{
    private final File sourceBaseDir;
    private final File storageBaseDir;

    public AbstractCallExtractor(File sourceBaseDir, File storageBaseDir)
    {
        this.storageBaseDir = storageBaseDir;
        this.sourceBaseDir = sourceBaseDir;
    }

    protected File getStorageFile(File baseDir, String hint)
    {
        File storage = new File(baseDir, hint);
        System.out.println(storage);
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


    protected File extractFromSourceBase(Class testClass, String hint)
    {
        String packageName = testClass.getPackage().getName().replace(".", "/");
        File baseDir = new File(this.sourceBaseDir, packageName);
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

    protected File extractFromStorageBase(String hint)
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
}
