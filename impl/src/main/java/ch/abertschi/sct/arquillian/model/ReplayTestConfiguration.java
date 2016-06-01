package ch.abertschi.sct.arquillian.model;

import ch.abertschi.sct.arquillian.client.ReplayConfiguration;

import java.util.List;

/**
 * Created by abertschi on 01/06/16.
 */
public class ReplayTestConfiguration
{
    private ReplayConfiguration classConfiguration;

    private List<ReplayConfiguration> methodConfigurations;

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
}
