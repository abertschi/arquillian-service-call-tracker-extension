package ch.abertschi.sct.arquillian.model;

import com.thoughtworks.xstream.XStream;

import java.util.List;

/**
 * Created by abertschi on 01/06/16.
 */
public class ExtensionConfiguration
{
    private List<RecordTestConfiguration> recordConfigurations;

    private List<ReplayTestConfiguration> replayConfigurations;

    public ExtensionConfiguration()
    {
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

    public String toXml()
    {
        return new XStream().toXML(this);
    }

    public static ExtensionConfiguration fromXml(String xml)
    {
        return (ExtensionConfiguration) new XStream().fromXML(xml);
    }
}
