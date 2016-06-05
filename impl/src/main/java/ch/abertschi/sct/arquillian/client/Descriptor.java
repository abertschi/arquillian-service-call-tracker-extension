package ch.abertschi.sct.arquillian.client;

import ch.abertschi.sct.arquillian.Constants;
import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.config.descriptor.api.ExtensionDef;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abertschi on 01/06/16.
 */
public class Descriptor
{
    @Inject
    @ApplicationScoped
    InstanceProducer<Descriptor> instanceProducer;

    @Inject
    Instance<ArquillianDescriptor> descriptor;

    private static Descriptor instance;

    private boolean enabled = true;
    private boolean recordingEnabled = true;
    private String recordingStorageBase = ".";
    private String recordingSourceType = null;
    private String recordingMode = null;

    private boolean replayingEnabled = true;
    private String replayingStorageBase = ".";
    private String replayingSourceType = null;
    private boolean replayingSkipDoubles = false;

    private boolean throwExceptionOnNotFound = true;
    private boolean throwExceptionOnIncompatibleReturnType = true;
    private String storageBase = null;

    public static Descriptor get()
    {
        if (instance == null)
        {
            throw new RuntimeException("Managed dependencies are not loaded. Observers in LoadableExtension are wrong configured!");
        }
        return instance;
    }

    private boolean getBoolean(ExtensionDef def, String name)
    {
        return get(def, name).toUpperCase().equals("TRUE");
    }

    private boolean has(ExtensionDef def, String name)
    {
        String val = def.getExtensionProperties().get(name);
        return val != null && val.trim().isEmpty();
    }

    private String get(ExtensionDef def, String name)
    {
        return def.getExtensionProperties().get(name);
    }

    void init(@Observes(precedence = 200) BeforeClass before)
    {
        instance = this;
        this.instanceProducer.set(instance);

        for (ExtensionDef e : this.descriptor.get().getExtensions())
        {
            if (e.getExtensionName().equals(Constants.EXT_NAME))
            {
                if (has(e, "enabled"))
                    this.enabled = getBoolean(e, "enabled");
                if (has(e, "throwExceptionOnNotFound"))
                    this.throwExceptionOnNotFound = getBoolean(e, "throwExceptionOnNotFound");
                if (has(e, "throwExceptionOnIncompatibleReturnType"))
                    this.throwExceptionOnIncompatibleReturnType = getBoolean(e, "throwExceptionOnIncompatibleReturnType");
                if (has(e, "storageBase"))
                    this.storageBase = get(e, "storageBase");
                if (has(e, "replayingEnabled"))
                    this.replayingEnabled = getBoolean(e, "replayingEnabled");
                if (has(e, "replayingStorageBase"))
                    this.replayingStorageBase = get(e, "replayingStorageBase");
                if (has(e, "replayingSourceType"))
                    this.replayingSourceType = get(e, "replayingSourceType");
                if (has(e, "replayingSkipDoubles"))
                    this.replayingSkipDoubles = getBoolean(e, "replayingSkipDoubles");
                if (has(e, "recordingEnabled"))
                    this.recordingEnabled = getBoolean(e, "recordingEnabled");
                if (has(e, "recordingStorageBase"))
                    this.recordingStorageBase = get(e, "recordingStorageBase");
                if (has(e, "recordingSourceType"))
                    this.recordingSourceType = get(e, "recordingSourceType");
                if (has(e, "recordingMode"))
                    this.recordingMode = get(e, "recordingMode");
            }
        }
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public boolean isRecordingEnabled()
    {
        return recordingEnabled;
    }

    public String getRecordingMode()
    {
        return recordingMode;
    }

    public String getRecordingSourceType()
    {
        return recordingSourceType;
    }

    public String getRecordingStorageBase()
    {
        return recordingStorageBase;
    }

    public boolean isReplayingEnabled()
    {
        return replayingEnabled;
    }

    public boolean isReplayingSkipDoubles()
    {
        return replayingSkipDoubles;
    }

    public String getReplayingSourceType()
    {
        return replayingSourceType;
    }

    public String getReplayingStorageBase()
    {
        return replayingStorageBase;
    }

    public String getStorageBase()
    {
        return storageBase;
    }

    public boolean isThrowExceptionOnIncompatibleReturnType()
    {
        return throwExceptionOnIncompatibleReturnType;
    }

    public boolean isThrowExceptionOnNotFound()
    {
        return throwExceptionOnNotFound;
    }

}
