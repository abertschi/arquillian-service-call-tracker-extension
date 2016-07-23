package ch.abertschi.sct.arquillian;

import ch.abertschi.sct.arquillian.annotation.RecordConfiguration;
import ch.abertschi.sct.arquillian.annotation.ReplayConfiguration;
import com.github.underscore.$;
import com.thoughtworks.xstream.XStream;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abertschi on 01/06/16.
 */
public class ExtensionConfiguration
{
    private List<RecordTestConfiguration> recordConfigurations = new ArrayList<>();

    private List<ReplayTestConfiguration> replayConfigurations = new ArrayList<>();

    public List<RecordConfiguration> getAllRecordConfigurations()
    {
        List<RecordConfiguration> recordings = new ArrayList<>();
        for (RecordTestConfiguration testConfig : recordConfigurations)
        {
            if (testConfig.getClassConfiguration() != null)
            {
                recordings.add(testConfig.getClassConfiguration());
            }
            if (!$.isEmpty(testConfig.getMethodConfigurations()))
            {
                recordings.addAll(testConfig.getMethodConfigurations());
            }
        }
        return recordings;
    }

    public List<ReplayConfiguration> getAllReplayingConfigurations()
    {
        List<ReplayConfiguration> replayings = new ArrayList<>();
        for (ReplayTestConfiguration testConfig : replayConfigurations)
        {
            if (testConfig.getClassConfiguration() != null)
            {
                replayings.add(testConfig.getClassConfiguration());
            }
            if (!$.isEmpty(testConfig.getMethodConfigurations()))
            {
                replayings.addAll(testConfig.getMethodConfigurations());
            }
        }
        return replayings;
    }

    public ReplayTestConfiguration getReplayTestConfiguration(Class<?> testClass)
    {
        List<ReplayTestConfiguration> config = $.filter(replayConfigurations, replayConfig -> replayConfig.isTestClass(testClass));
        return $.isEmpty(config) ? null : config.get(0);
    }

    public RecordTestConfiguration getRecordTestConfiguration(Class<?> testClass)
    {
        List<RecordTestConfiguration> config = $.filter(recordConfigurations, recordConfig -> recordConfig.isTestClass(testClass));
        return $.isEmpty(config) ? null : config.get(0);
    }

    public ReplayConfiguration getReplayConfiguration(Class<?> testClass, Method testMethod)
    {
        ReplayConfiguration result = null;

        ReplayTestConfiguration testConfig = getReplayTestConfiguration(testClass);
        if (testConfig != null)
        {
            ReplayConfiguration methodConfig = testConfig.getMethodConfiguration(testClass, testMethod);
            if (methodConfig != null)
            {
                result = methodConfig;
            }
            else
            {
                result = testConfig.getClassConfiguration();
            }
        }
        return result;
    }

    public RecordConfiguration getRecordConfiguration(Class<?> testClass, Method testMethod)
    {
        RecordConfiguration result = null;

        RecordTestConfiguration testConfig = getRecordTestConfiguration(testClass);
        if (testConfig != null)
        {
            RecordConfiguration methodConfig = testConfig.getMethodConfiguration(testClass, testMethod);
            if (methodConfig != null)
            {
                result = methodConfig;
            }
            else
            {
                result = testConfig.getClassConfiguration();
            }
        }
        return result;
    }

    public String toXml()
    {
        return new XStream().toXML(this);
    }

    public static ExtensionConfiguration fromXml(String xml)
    {
        return (ExtensionConfiguration) new XStream().fromXML(xml);
    }

    public List<RecordTestConfiguration> getRecordConfigurations()
    {
        return recordConfigurations;
    }

    public ExtensionConfiguration setRecordConfigurations(List<RecordTestConfiguration> recordConfigurations)
    {
        this.recordConfigurations = recordConfigurations;
        return this;
    }

    public List<ReplayTestConfiguration> getReplayConfigurations()
    {
        return replayConfigurations;
    }

    public ExtensionConfiguration setReplayConfigurations(List<ReplayTestConfiguration> replayConfigurations)
    {
        this.replayConfigurations = replayConfigurations;
        return this;
    }
}
