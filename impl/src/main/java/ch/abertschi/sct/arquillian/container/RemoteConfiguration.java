package ch.abertschi.sct.arquillian.container;

import ch.abertschi.sct.arquillian.ExtensionConfiguration;

/**
 * Created by abertschi on 29/06/16.
 */
public class RemoteConfiguration
{
    private ExtensionConfiguration configuration;

    public ExtensionConfiguration getConfiguration()
    {
        return configuration;
    }

    public RemoteConfiguration setConfiguration(ExtensionConfiguration configuration)
    {
        this.configuration = configuration;
        return this;
    }
}
