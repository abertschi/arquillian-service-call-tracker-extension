package ch.abertschi.sct.arquillian.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.abertschi.sct.arquillian.resource.model.Resource;

/**
 *
 * @author Andrin Bertschi
 */
public class ResourceData
{
    private List<Resource> recordingResourcesOnClient = new ArrayList<>();

    private List<Resource> replayingResources = new ArrayList<>();

    private List<Resource> recordingResources = new ArrayList<>();

    public Map<String, Resource> getReplayingResourcesAsMap()
    {
        Map<String, Resource> map = new HashMap<>();
        for (Resource resource : replayingResources)
        {
            map.put(resource.getName(), resource);
        }
        return map;
    }

    public Map<String, Resource> getRecordingResourcesAsMap()
    {
        Map<String, Resource> map = new HashMap<>();
        for (Resource resource : recordingResources)
        {
            map.put(resource.getName(), resource);
        }
        return map;
    }

    public Map<String, Resource> getRecordingResoucesOnClientAsMap()
    {
        Map<String, Resource> map = new HashMap<>();
        for (Resource resource : recordingResourcesOnClient)
        {
            map.put(resource.getName(), resource);
        }
        return map;
    }

    public List<Resource> getReplayingResources()
    {
        return replayingResources;
    }

    public void setReplayingResources(List<Resource> replayingResources)
    {
        this.replayingResources = replayingResources;
    }

    public List<Resource> getRecordingResources()
    {
        return recordingResources;
    }

    public void setRecordingResources(List<Resource> recordingResources)
    {
        this.recordingResources = recordingResources;
    }

    public List<Resource> getRecordingResourcesOnClient()
    {
        return recordingResourcesOnClient;
    }

    public void setRecordingResourcesOnClient(List<Resource> recordingResourcesOnClient)
    {
        this.recordingResourcesOnClient = recordingResourcesOnClient;
    }

}
