package ch.abertschi.sct.arquillian.resource.naming;

import java.lang.reflect.Method;

import org.jboss.arquillian.test.spi.TestClass;

import ch.abertschi.sct.arquillian.api.SctInterceptTo;
import ch.abertschi.sct.arquillian.api.SctInterceptBy;

/**
 * @author Andrin Bertschi
 */
public class ResourceNaming
{
    private TestClass testClass;

    private Method testMethod;

    private String name;

    public ResourceNaming(TestClass testClass, Method m, String suffix)
    {
        this.testClass = testClass;
        this.testMethod = m;
        this.name = suffix;
    }

    public ResourceNaming(TestClass testClass, Method m)
    {
        this.testClass = testClass;
        this.testMethod = m;
    }

    public String create()
    {
        if (this.testClass == null | this.testMethod == null)
        {
            throw new IllegalArgumentException("testClass and testMethod must be set!");
        }
        StringBuilder build = new StringBuilder();
        build.append(this.testClass.getName() + "." + this.testMethod.getName());

        if (this.name != null && !this.name.isEmpty())
        {
            build.append("." + this.name);
        }
        return build.toString();
    }
}
