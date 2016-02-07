package org.sct.arquillian.resource.extraction;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.asset.FileAsset;

import org.sct.arquillian.resource.naming.ResourceNaming;
import org.sct.arquillian.AsctConstants;
import org.sct.arquillian.api.SctInterceptBy;
import org.sct.arquillian.client.AsctLocalExtension.AsctDescriptor;
import org.sct.arquillian.resource.model.ResourceImpl;
import org.sct.arquillian.util.exception.AsctException;

/**
 * Extractor capable extracting information specified by {@link SctInterceptBy}.
 *
 * @author Andrin Bertschi
 */
public class SctInterceptByExtractor extends
        AbstractAnnotationExtractor<SctInterceptBy, ResourceImpl>
{

    private File mockingDirectoryRoot;

    public SctInterceptByExtractor(AsctDescriptor descriptor)
    {
        super(SctInterceptBy.class, ResourceImpl.class);

        this.mockingDirectoryRoot = new File(".",
                descriptor.getProperties().get(AsctConstants.EXT_PROPERTY_MOCKING_ROOT));
    }

    @Override
    public ResourceImpl extractAsResource(TestClass testClass, Method testMethod, Annotation annotation)
    {
        File file = null;
        ResourceImpl resource = null;
        try
        {
            SctInterceptBy annot = (SctInterceptBy) annotation;
            resource = new ResourceImpl();
            file = extractLocation(annot);
            ensureIfFileExist(file, testClass, testMethod);
            resource.setPath(file.getPath());
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

    private void handleException(TestClass testClass, Method testMethod, File file, Exception e)
    {
        AsctException asctE = new AsctException(e);
        asctE.add("test-class", testClass.getJavaClass().getCanonicalName());
        asctE.add("test-method", testMethod.getName());
        asctE.add("resource-annotation", SctInterceptBy.class.getName());
        asctE.add("resource-location", (file != null) ? file.getAbsolutePath() : null);
        throw asctE;
    }

    private void ensureIfFileExist(File file, TestClass testClass, Method testMethod)
    {
        if (!file.exists())
        {
            String m = String.format("Resource %s specified in %s was not found on client machine.",
                    file.getAbsolutePath(), SctInterceptBy.class.getName());

            throw new RuntimeException(m);
        }
    }

    private File extractLocation(SctInterceptBy mockedData)
    {
        try
        {
            return new File(this.mockingDirectoryRoot, mockedData.value());
        }
        catch (Exception e)
        {
            throw new RuntimeException(String.format("Not able to create file using %s and %s",
                    this.mockingDirectoryRoot.getAbsoluteFile(), mockedData.value()));
        }
    }
}
