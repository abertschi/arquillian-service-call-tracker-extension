package ch.abertschi.sct.arquillian.annotation;

import ch.abertschi.sct.api.Configuration;

/**
 * Created by abertschi on 01/06/16.
 */
public class ReplayConfiguration
{
    private boolean enabled;

    private String name;

    private boolean throwExceptionOnNotFound;

    private boolean throwExceptionOnIncompatibleReturnType;

    private Configuration.INPUT_SOURCE sourceType;

    private String path;

    public String getPath()
    {
        return path;
    }

    public ReplayConfiguration setPath(String path)
    {
        this.path = path;
        return this;
    }

    public boolean isThrowExceptionOnIncompatibleReturnType()
    {
        return throwExceptionOnIncompatibleReturnType;
    }

    public ReplayConfiguration setThrowExceptionOnIncompatibleReturnType(boolean throwExceptionOnIncompatibleReturnType)
    {
        this.throwExceptionOnIncompatibleReturnType = throwExceptionOnIncompatibleReturnType;
        return this;
    }

    public boolean isThrowExceptionOnNotFound()
    {
        return throwExceptionOnNotFound;
    }

    public ReplayConfiguration setThrowExceptionOnNotFound(boolean throwExceptionOnNotFound)
    {
        this.throwExceptionOnNotFound = throwExceptionOnNotFound;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public ReplayConfiguration setName(String name)
    {
        this.name = name;
        return this;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public ReplayConfiguration setEnabled(boolean enabled)
    {
        this.enabled = enabled;
        return this;
    }

    public Configuration.INPUT_SOURCE getSourceType()
    {
        return sourceType;
    }

    public ReplayConfiguration setSourceType(Configuration.INPUT_SOURCE sourceType)
    {
        this.sourceType = sourceType;
        return this;
    }
}
