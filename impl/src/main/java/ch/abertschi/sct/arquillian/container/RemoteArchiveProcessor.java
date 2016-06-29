package ch.abertschi.sct.arquillian.container;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import ch.abertschi.sct.arquillian.ExtensionConfiguration;
import org.apache.commons.io.IOUtils;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;
import org.jboss.shrinkwrap.api.asset.UrlAsset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.abertschi.sct.arquillian.Constants;

public class RemoteArchiveProcessor
{

    private static final Logger LOG = LoggerFactory.getLogger(RemoteArchiveProcessor.class);

    @Inject
    @ApplicationScoped
    private InstanceProducer<RemoteConfiguration> remoteConfigurationProducer;

    public void init(@Observes BeforeSuite suite) throws IOException
    {
        RemoteConfiguration remoteConfig = new RemoteConfiguration();
        remoteConfig.setConfiguration(loadConfiguration());
        remoteConfigurationProducer.set(remoteConfig);
    }

    private ExtensionConfiguration loadConfiguration()
    {
        ExtensionConfiguration config = null;
        try
        {
            String xml = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResource(Constants.CONFIGURATION_FILE).openStream());
            if (xml != null || !xml.isEmpty())
            {
                config = ExtensionConfiguration.fromXml(xml);
            }
        }
        catch (IOException e)
        {
            // treat any io exception as if file was empty
        }
        return config;
    }

//    private void loadRecordingFiles() throws IOException
//    {
//        /*
//         * Arquillian container can be executed on another host than Arquillian client.
//         * serverRecordings store recording files on container host, whereas
//         * clientRecordings store recording files on client.
//         * At the end of test executions, serverRecordings must be transmitted to client.
//         */
//        List<Resource> serverRecordings = new ArrayList<>();
//        List<Resource> clientRecordings = new ArrayList<>();
//        for (Map.Entry<String, String> e : loadResourcesFromJar(Constants.CONFIGURATION_FILE).entrySet())
//        {
//            File temp = File.createTempFile(e.getKey(), ".xml");
//            temp.deleteOnExit();
//            ResourceImpl containerResource = new ResourceImpl();
//            containerResource.setAsset(new UrlAsset(temp.getAbsoluteFile().toURI().toURL()));
//            containerResource.setPath(temp.getPath());
//            containerResource.setName(e.getKey());
//            serverRecordings.add(containerResource);
//
//            ResourceImpl clientResource = new ResourceImpl();
//            clientResource.setPath(e.getValue());
//            clientResource.setName(e.getKey());
//            clientRecordings.add(clientResource);
//
//            LOG.debug("Resolving index resource using {}={}", new Object[]{e.getKey(), e.getValue()});
//        }
//        mFilesResources.setRecordingResources(serverRecordings);
//        mFilesResources.setRecordingResourcesOnClient(clientRecordings);
//    }
//
//    private void loadReplayingFiles()
//    {
//        List<Resource> replaying = new ArrayList<>();
//        for (Map.Entry<String, String> e : loadResourcesFromJar(Constants.RESOURCE_MOCKING_INDEX).entrySet())
//        {
//            ResourceImpl resource = new ResourceImpl();
//            resource.setAsset(new UrlAsset(Thread.currentThread().getContextClassLoader().getResource(e.getValue())));
//            resource.setPath(e.getValue());
//            resource.setName(e.getKey());
//            replaying.add(resource);
//            LOG.debug("Resolving index resource using {}={}", new Object[]{e.getKey(), e.getValue()});
//        }
//        mFilesResources.setReplayingResources(replaying);
//    }
//
//    private Map<String, String> loadResourcesFromJar(String file)
//    {
//        Properties prop = new Properties();
//        try
//        {
//            InputStream in = Thread.currentThread().getContextClassLoader().getResource(file).openStream();
//            prop.load(in);
//            in.close();
//        }
//        catch (IOException e)
//        {
//            String m = String.format("Not able to parse properties from %s", Constants.CONFIGURATION_FILE);
//            AsctException asctE = new AsctException(m, e);
//            throw asctE;
//        }
//        return castToStringEntires(prop);
//    }
//
//    @SuppressWarnings("unchecked")
//    private Map<String, String> castToStringEntires(@SuppressWarnings("rawtypes") Map map)
//    {
//        return (Map<String, String>) map;
//    }
}
