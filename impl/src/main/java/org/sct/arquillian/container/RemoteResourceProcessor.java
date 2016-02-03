package org.sct.arquillian.container;

import java.util.List;

import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;
import org.sct.arquillian.resource.LocationResolver;
import org.sct.arquillian.resource.index.ResourceIndexResolverImpl;
import org.sct.arquillian.util.NotManagedByContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.sct.arquillian.AsctConstants;
import org.sct.arquillian.resource.model.Resource;

/**
 * Extracts resources from archive in execution on remote.
 * 
 * @author Andrin Bertschi
 * 
 */
public class RemoteResourceProcessor {
    
    private static final Logger LOG = LoggerFactory.getLogger(RemoteResourceProcessor.class);

    @Inject
    @ApplicationScoped
    private InstanceProducer<ResourceData> dataProducer;

    private ResourceData resourceData;

    public RemoteResourceProcessor() {
    }

    @NotManagedByContainer
    public RemoteResourceProcessor(InstanceProducer<ResourceData> dataProducer) {
        this.dataProducer = dataProducer;
    }

    public void loadSerializedProperties(@Observes BeforeSuite event) {
        this.resourceData = new ResourceData();
        loadMockDatasets();
        loadRecordDatasets();
        this.dataProducer.set(this.resourceData);
    }

    private void loadRecordDatasets() {
        List<Resource> resources =
                new ResourceIndexResolverImpl()
                        .resolveIndex(AsctConstants.RESOURCE_RECORDING_INDEX,
                                new LocationResolver.ClientLocationResolver());
        this.resourceData.setRecordingResources(resources);
        
        if (resources != null) {
            LOG.debug("{} resources located in {} resolved on remote.", 
                    new Object[] {resources.size(), AsctConstants.RESOURCE_RECORDING_INDEX});
        }
    }

    private void loadMockDatasets() {
        List<Resource> resources =
                new ResourceIndexResolverImpl()
                        .resolveIndex(AsctConstants.RESOURCE_MOCKING_INDEX,
                                new LocationResolver.RemoteLocationResolver());
        this.resourceData.setMockingResources(resources);
        
        if (resources != null) {
            LOG.debug("{} resources located in {} resolved on remote.", 
                    new Object[] {resources.size(), AsctConstants.RESOURCE_MOCKING_INDEX});
        }
    }
}
