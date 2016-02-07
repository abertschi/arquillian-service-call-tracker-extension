package org.sct.arquillian.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sct.arquillian.resource.model.Resource;

/**
 * POJO representing all {@code Resource} of deployment.
 * 
 * @author Andrin Bertschi
 * 
 */
public class ResourceData {

    private List<Resource> mockingResources = new ArrayList<>();

    private List<Resource> recordingResources = new ArrayList<>();

    public Map<String, Resource> getMockingResourcesAsMap() {
        Map<String, Resource> map = new HashMap<>();
        for (Resource resource : mockingResources) {
            map.put(resource.getName(), resource);
        }
        return map;
    }

    public Map<String, Resource> getRecordingResourcesAsMap() {
        Map<String, Resource> map = new HashMap<>();
        for (Resource resource : recordingResources) {
            map.put(resource.getName(), resource);
        }
        return map;
    }

    public List<Resource> getMockingResources() {
        return mockingResources;
    }

    public void setMockingResources(List<Resource> mockingResources) {
        this.mockingResources = mockingResources;
    }

    public List<Resource> getRecordingResources() {
        return recordingResources;
    }

    public void setRecordingResources(List<Resource> recordingResources) {
        this.recordingResources = recordingResources;
    }

}
