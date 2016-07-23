package ch.abertschi.sct.arquillian.annotation;

import ch.abertschi.sct.api.Configuration;

import java.lang.reflect.Method;

/**
 * Created by abertschi on 01/06/16.
 */
public class RecordConfiguration
{
    private boolean enabled;

    private String origin;

    private String path;

    private String containerPath;

    private Configuration.INPUT_SOURCE sourceType;

    private Configuration.RECORDING_MODE mode;

    private boolean skipDoubles;

    public RecordConfiguration(Class<?> testClass, Method testMethod)
    {
        this.origin = createOrigin(testClass, testMethod);
    }

    private String createOrigin(Class<?> type, Method method)
    {
        return String.format("%s%s", type.getName(), method != null ? method.getName() : "");
    }

    public boolean isOrigin(Class<?> type, Method method)
    {
        return createOrigin(type, method).equals(this.origin);
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

    public String getOrigin()
    {
        return origin;
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

    public String getContainerPath()
    {
        return containerPath;
    }

    public RecordConfiguration setContainerPath(String containerPath)
    {
        this.containerPath = containerPath;
        return this;
    }
}
