package org.sct.arquillian.resource.index;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.jboss.shrinkwrap.api.asset.UrlAsset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.sct.arquillian.resource.LocationResolver;
import org.sct.arquillian.resource.LocationResolver.RemoteLocationResolver;
import org.sct.arquillian.resource.model.Resource;
import org.sct.arquillian.resource.model.ResourceImpl;
import org.sct.arquillian.util.exception.AsctException;

/**
 * @author Andrin Bertschi
 */
public class ResourceIndexResolverImpl implements ResourceIndexResolver {
    
    private static final Logger LOG = LoggerFactory.getLogger(ResourceIndexResolverImpl.class);

    @Override
    public List<Resource> resolveIndex(String location, LocationResolver resolver) {
        List<Resource> resources = new ArrayList<>();
        Map<String, String> properties = load(new RemoteLocationResolver().resolve(location));
        for (Entry<String, String> e : properties.entrySet()) {
            ResourceImpl resource = new ResourceImpl();
            resource.setAsset(new UrlAsset(resolver.resolve(e.getValue())));
            resource.setLocation(e.getValue());
            resource.setBusinessKey(e.getKey());
            resources.add(resource);
            
            LOG.debug("Resolving index resource using {}={}", new Object[] {e.getKey(), e.getValue()});
        }
        return resources;
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

}
