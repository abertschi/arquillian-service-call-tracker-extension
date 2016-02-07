package org.sct.arquillian.container;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jboss.arquillian.container.test.spi.command.CommandService;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.AfterSuite;
import org.jboss.arquillian.test.spi.event.suite.Before;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;

import org.sct.api.SctConfigurator;
import org.sct.arquillian.ResourceCommand;
import org.sct.arquillian.resource.naming.ResourceBusinessNaming;
import org.sct.arquillian.resource.LocationResolver.ClientLocationResolver;
import org.sct.arquillian.resource.LocationResolver.RemoteLocationResolver;
import org.sct.arquillian.resource.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sets configuration required by {@code sct} for current test execution. See
 * {@link SctConfiguration}.
 * 
 * @author Andrin Bertschi
 * 
 */
public class SctConfigurationEnricher {

    @Inject
    Instance<ResourceData> configurations;

    @Inject
    Instance<CommandService> commandService;

    private static final Logger LOG = LoggerFactory.getLogger(SctConfigurationEnricher.class);

    private SctConfigurationImpl sctConfig;

    public void init(@Observes BeforeSuite before) {
        this.sctConfig = new SctConfigurationImpl();
        SctConfigurator.getInstance().setConfiguration(sctConfig);
    }

    public void beforeTest(@Observes Before before) throws MalformedURLException {
        ResourceBusinessNaming naming =
                new ResourceBusinessNaming(before.getTestClass(), before.getTestMethod());

        System.out.println("hi");
        String businessKey = naming.create();
        initBoundary(this.sctConfig);

        setMockingBoundary(businessKey, this.sctConfig);
        setRecordingBoundary(businessKey, this.sctConfig);
    }

    public void afterTest(@Observes AfterSuite after) throws URISyntaxException, IOException
    {
        for (Resource r : configurations.get().getRecordingResources()) {
            String content = convertStreamToString(r.getAsset().openStream());
            commandService.get().execute(new ResourceCommand(r.getName(), r.getPath(), content));
        }
        initBoundary(this.sctConfig);
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


    private void setMockingBoundary(String id, SctConfigurationImpl exec)
            throws MalformedURLException {
        if (this.configurations.get().getMockingResourcesAsMap().containsKey(id)) {
            Resource res = this.configurations.get().getMockingResourcesAsMap().get(id);
            exec.setResponseLoading(true);

            // TODO: Not responsibility of this class -> move to resource!
            exec.setResponseLoadingUrl(new RemoteLocationResolver().resolve(res.getPath()));
        }
    }

    private void setRecordingBoundary(String id, SctConfigurationImpl exec)
            throws MalformedURLException {
        if (this.configurations.get().getRecordingResourcesAsMap().containsKey(id)) {
            Resource res = this.configurations.get().getRecordingResourcesAsMap().get(id);
            exec.setCallRecording(true);
            exec.setCallRecordingUrl(new ClientLocationResolver().resolve(res.getPath()));
        }
    }

    private void initBoundary(SctConfigurationImpl exec) {
        exec.setResponseLoadingUrl(null);
        exec.setCallRecordingUrl(null);
        exec.setResponseLoading(false);
        exec.setCallRecording(false);
    }

}
