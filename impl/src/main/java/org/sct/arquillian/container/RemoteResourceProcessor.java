package org.sct.arquillian.container;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;
import org.jboss.shrinkwrap.api.asset.UrlAsset;
import org.sct.arquillian.resource.LocationResolver;
import org.sct.arquillian.resource.index.ResourceIndexResolverImpl;
import org.sct.arquillian.resource.model.ResourceImpl;
import org.sct.arquillian.util.exception.AsctException;
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
    InstanceProducer<ResourceData> dataProducer;

    private ResourceData resourceData;

    public void loadSerializedProperties(@Observes BeforeSuite event) throws IOException
    {
        this.resourceData = new ResourceData();
        loadMockDatasets();
        loadRecordDatasets();
        this.dataProducer.set(this.resourceData);
    }

    private Map<String, String> load(URL resource) {
        Properties prop = new Properties();
        try {
            InputStream in = resource.openStream();
            prop.load(in);
            in.close();
        } catch (IOException e) {
            String m = String.format("Not able to parse properties from %s", resource.getPath());
            AsctException asctE = new AsctException(m, e);
            throw asctE;
        }
        return castToStringEntires(prop);
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> castToStringEntires(@SuppressWarnings("rawtypes") Map map) {
        return (Map<String, String>) map;
    }

    private void loadRecordDatasets() throws IOException
    {
        List<Resource> resources = new ArrayList<>();
        Map<String, String> properties = load(Thread.currentThread().getContextClassLoader().getResource(AsctConstants.RESOURCE_RECORDING_INDEX));
        for (Map.Entry<String, String> e : properties.entrySet()) {
            ResourceImpl resource = new ResourceImpl();
            System.out.println(e.getKey());
            File temp = File.createTempFile(e.getKey(), ".xml");
            resource.setAsset(new UrlAsset(temp.getAbsoluteFile().toURI().toURL()));
            resource.setPath(e.getValue());
            resource.setName(e.getKey());
            resources.add(resource);

            LOG.debug("Resolving index resource using {}={}", new Object[] {e.getKey(), e.getValue()});
        }

        resourceData.setRecordingResources(resources);
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
