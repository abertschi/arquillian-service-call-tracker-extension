package ch.abertschi.sct.arquillian.resource.extraction;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.asset.FileAsset;

import ch.abertschi.sct.arquillian.AsctConstants;
import ch.abertschi.sct.arquillian.api.SctInterceptBy;
import ch.abertschi.sct.arquillian.api.SctInterceptTo;
import ch.abertschi.sct.arquillian.client.AsctLocalExtension;
import ch.abertschi.sct.arquillian.resource.model.ResourceImpl;
import ch.abertschi.sct.arquillian.resource.naming.ResourceNaming;
import ch.abertschi.sct.arquillian.util.exception.AsctException;

/**
 * Extractor capable extracting information specified by {@link SctInterceptTo}.
 *
 * @author Andrin Bertschi
 */
public class SctInterceptToExtractor extends
        AbstractAnnotationExtractor<SctInterceptTo, ResourceImpl>
{
    private File directoryRoot;

    public SctInterceptToExtractor(AsctLocalExtension.AsctDescriptor descriptor)
    {
        super(SctInterceptTo.class, ResourceImpl.class);
        this.directoryRoot = new File("./", descriptor.getProperties().get(
                AsctConstants.EXT_PROPERTY_RECORDING_ROOT));
    }

    @Override
    public ResourceImpl extractAsResource(TestClass testClass, Method testMethod, Annotation annotation)
    {
        File file = null;
        ResourceImpl resource = null;
        try
        {
            SctInterceptTo annot = (SctInterceptTo) annotation;
            resource = new ResourceImpl();
            file = extractLocation(annot);
            createFile(file, testClass);
            resource.setPath(file.getAbsolutePath());
            resource.setAsset(new FileAsset(file));
            ResourceNaming naming = new ResourceNaming(testClass, testMethod);
            resource.setName(naming.create());
        }
        catch (Exception e)
        {
            handleException(testClass, testMethod, file, e);
        }
        return resource;
    }

    private void createFile(File file, TestClass testClass)
    {
        try
        {
            file.getParentFile().mkdirs();
            if (file.exists())
            {
                file.delete();
            }
            file.createNewFile();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Not able to create file " + file.getAbsolutePath(), e);
        }
    }

    private File extractLocation(SctInterceptTo annot)
    {
        try
        {
            return new File(this.directoryRoot, annot.value());
        }
        catch (Exception e)
        {
            throw new RuntimeException(String.format("Not able to create file using %s and %s",
                    this.directoryRoot.getAbsoluteFile(), annot.value()), e);
        }
    }

    private void handleException(TestClass testClass, Method testMethod, File file, Exception e)
    {
        AsctException asctE = new AsctException(e);
        asctE.add("test-class", testClass.getJavaClass().getCanonicalName());
        asctE.add("test-method", testMethod.getName());
        asctE.add("resource-annotation", SctInterceptBy.class.getName());
        asctE.add("resource-location", (file != null) ? file.getAbsolutePath() : null);
        throw asctE;
    }

}