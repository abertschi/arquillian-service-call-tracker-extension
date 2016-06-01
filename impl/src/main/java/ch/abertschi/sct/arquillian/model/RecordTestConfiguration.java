package ch.abertschi.sct.arquillian.model;

import ch.abertschi.sct.arquillian.client.RecordConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abertschi on 01/06/16.
 */
public class RecordTestConfiguration
{
    private RecordConfiguration classConfiguration;

    private List<RecordConfiguration> methodConfigurations = new ArrayList<>();

    public RecordTestConfiguration()
    {
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
}
