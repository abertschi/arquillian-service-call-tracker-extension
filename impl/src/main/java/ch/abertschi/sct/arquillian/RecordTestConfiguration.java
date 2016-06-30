package ch.abertschi.sct.arquillian;

import ch.abertschi.sct.arquillian.annotation.RecordConfiguration;
import ch.abertschi.sct.arquillian.annotation.ReplayConfiguration;
import com.github.underscore.$;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abertschi on 01/06/16.
 */
public class RecordTestConfiguration
{
    private RecordConfiguration classConfiguration;

    private List<RecordConfiguration> methodConfigurations = new ArrayList<>();

    private String testClassName;

    public RecordTestConfiguration(Class<?> testClass)
    {
        this.testClassName = testClass.getName();
    }

    public RecordConfiguration getMethodConfiguration(Class<?> testClass, Method testMethod)
    {
        List<RecordConfiguration> config = $.filter(methodConfigurations, method -> method.isOrigin(testClass, testMethod));
        return $.isEmpty(config) ? null : config.get(0);
    }

    public List<RecordConfiguration> getMethodConfigurations()
    {
        return methodConfigurations;
    }

    public RecordTestConfiguration setMethodConfigurations(List<RecordConfiguration> methodConfigurations)
    {
        this.methodConfigurations = methodConfigurations;
        return this;
    }

    public RecordConfiguration getClassConfiguration()
    {
        return classConfiguration;
    }

    public RecordTestConfiguration setClassConfiguration(RecordConfiguration classConfiguration)
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
