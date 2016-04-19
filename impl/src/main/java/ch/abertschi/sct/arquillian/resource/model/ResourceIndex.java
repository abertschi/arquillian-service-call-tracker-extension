package ch.abertschi.sct.arquillian.resource.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.StringAsset;

import ch.abertschi.sct.arquillian.util.exception.AsctException;

/**
 * @author Andrin Bertschi
 */
public class ResourceIndex implements Resource
{
    private Map<String, String> index = new HashMap<>();

    private String location;

    @Override
    public Asset getAsset()
    {
        return new StringAsset(readContent());
    }

    public String readContent()
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try
        {
            Properties props = new Properties();
            props.putAll(this.index);
            props.store(out, getPath());
            return out.toString();
        }
        catch (IOException e)
        {
            throw new AsctException("Not able to write Resource index to " + getPath(), e);
        }
    }

    public void putIndex(String key, String value)
    {
        this.index.put(key, value);
    }

    @Override
    public String getPath()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    @Override
    public String getName()
    {
        return null;
    }
}
