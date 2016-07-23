package ch.abertschi.sct.arquillian.annotation;

import ch.abertschi.sct.api.Configuration;

import java.lang.reflect.Method;

/**
 * Created by abertschi on 01/06/16.
 */
public class ReplayConfiguration
{
    private boolean enabled;

    private String origin;

    private boolean throwExceptionOnNotFound;

    private boolean throwExceptionOnIncompatibleReturnType;

    private Configuration.INPUT_SOURCE sourceType;

    private String path;

    public ReplayConfiguration(Class<?> testClass, Method testMethod)
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

    public String getOrigin()
    {
        return origin;
    }

    public ReplayConfiguration setOrigin(String origin)
    {
        this.origin = origin;
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
