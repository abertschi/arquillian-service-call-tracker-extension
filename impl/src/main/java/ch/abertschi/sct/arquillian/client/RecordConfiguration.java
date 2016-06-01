package ch.abertschi.sct.arquillian.client;

import ch.abertschi.sct.api.Configuration;

import java.net.URL;

/**
 * Created by abertschi on 01/06/16.
 */
public class RecordConfiguration
{
    private boolean enabled;

    private String name;

    private String path;

    private Configuration.INPUT_SOURCE sourceType;

    private Configuration.RECORDING_MODE mode;

    private boolean skipDoubles;

    public RecordConfiguration()
    {

    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public RecordConfiguration setEnabled(boolean enabled)
    {
        this.enabled = enabled;
        return this;
    }

    public Configuration.RECORDING_MODE getMode()
    {
        return mode;
    }

    public RecordConfiguration setMode(Configuration.RECORDING_MODE mode)
    {
        this.mode = mode;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public RecordConfiguration setName(String name)
    {
        this.name = name;
        return this;
    }

    public String getPath()
    {
        return path;
    }

    public RecordConfiguration setPath(String path)
    {
        this.path = path;
        return this;
    }

    public boolean isSkipDoubles()
    {
        return skipDoubles;
    }

    public RecordConfiguration setSkipDoubles(boolean skipDoubles)
    {
        this.skipDoubles = skipDoubles;
        return this;
    }

    public Configuration.INPUT_SOURCE getSourceType()
    {
        return sourceType;
    }

    public RecordConfiguration setSourceType(Configuration.INPUT_SOURCE sourceType)
    {
        this.sourceType = sourceType;
        return this;
    }
}
