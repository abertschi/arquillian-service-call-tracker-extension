package ch.abertschi.sct.arquillian;

import ch.abertschi.sct.arquillian.annotation.ReplayConfiguration;
import com.github.underscore.$;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by abertschi on 01/06/16.
 */
public class ReplayTestConfiguration
{
    private ReplayConfiguration classConfiguration;

    private List<ReplayConfiguration> methodConfigurations;

    private String testClassName;

    public ReplayTestConfiguration(Class<?> testClass)
    {
        this.testClassName = testClass.getName();
    }

    public ReplayConfiguration getMethodConfiguration(Class<?> testClass, Method testMethod)
    {
        List<ReplayConfiguration> config = $.filter(methodConfigurations, method -> method.isOrigin(testClass, testMethod));
        return $.isEmpty(config) ? null : config.get(0);
    }

    public List<ReplayConfiguration> getMethodConfigurations()
    {
        return methodConfigurations;
    }

    public ReplayTestConfiguration setMethodConfigurations(List<ReplayConfiguration> methodConfigurations)
    {
        this.methodConfigurations = methodConfigurations;
        return this;
    }

    public ReplayConfiguration getClassConfiguration()
    {
        return classConfiguration;
    }

    public ReplayTestConfiguration setClassConfiguration(ReplayConfiguration classConfiguration)
    {
        this.classConfiguration = classConfiguration;
        return this;
    }

    public String getTestClassName()
    {
        return testClassName;
    }

    public boolean isTestClass(Class<?> testClass)
    {
        return testClass.getName().equals(this.testClassName);
    }

}
