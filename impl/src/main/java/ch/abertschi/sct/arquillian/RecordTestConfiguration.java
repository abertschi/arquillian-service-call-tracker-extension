package ch.abertschi.sct.arquillian;

import ch.abertschi.sct.arquillian.annotation.RecordConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abertschi on 01/06/16.
 */
public class RecordTestConfiguration
{
    private RecordConfiguration classConfiguration;

    private List<RecordConfiguration> methodConfigurations = new ArrayList<>();

    private String origin;

    public static String createOrigin(Class<?> type)
    {
        return type.getCanonicalName();
    }

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

    public String getOrigin()
    {
        return origin;
    }

    public RecordTestConfiguration setOrigin(String origin)
    {
        this.origin = origin;
        return this;
    }
}
