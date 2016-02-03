package org.sct.arquillian.resource.index;

import java.util.List;

import org.sct.arquillian.resource.model.ResourceIndex;
import org.sct.arquillian.resource.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrin Bertschi
 */
public class ResourceIndexBuilderImpl implements ResourceIndexBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceIndexBuilderImpl.class);
    
    @Override
    public Resource createIndex(String location, List<Resource> resources) {
        ResourceIndex index = new ResourceIndex();
        index.setLocation(location);
        for (Resource resource : resources) {
            String key = resource.getBusinessKey();
            String value = resource.getLocation();
            index.putIndex(key, value);
            
            LOG.debug("Creating index resource using {}={}", new Object[] {key, value});
        }
        return index;
    }
}
