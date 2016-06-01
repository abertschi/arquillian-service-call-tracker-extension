package ch.abertschi.sct.arquillian;

import ch.abertschi.sct.arquillian.annotation.ReplayConfiguration;

import java.util.List;

/**
 * Created by abertschi on 01/06/16.
 */
public class ReplayTestConfiguration
{
    private ReplayConfiguration classConfiguration;

    private List<ReplayConfiguration> methodConfigurations;

    private String origin;

    public static String createOrigin(Class<?> type)
    {
        return type.getCanonicalName();
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

    public String getOrigin()
    {
        return origin;
    }

    public ReplayTestConfiguration setOrigin(String origin)
    {
        this.origin = origin;
        return this;
    }
}
