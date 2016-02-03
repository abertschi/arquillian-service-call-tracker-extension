package org.sct.arquillian.container;

import java.net.MalformedURLException;

import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.After;
import org.jboss.arquillian.test.spi.event.suite.Before;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;

import org.sct.api.SctConfigurator;
import org.sct.arquillian.resource.naming.ResourceBusinessNaming;
import org.sct.arquillian.resource.LocationResolver.ClientLocationResolver;
import org.sct.arquillian.resource.LocationResolver.RemoteLocationResolver;
import org.sct.arquillian.resource.model.Resource;
import org.sct.arquillian.util.NotManagedByContainer;

/**
 * Sets configuration required by {@code sct} for current test execution. See
 * {@link SctConfiguration}.
 * 
 * @author Andrin Bertschi
 * 
 */
public class SctConfigurationEnricher {

    @Inject
    private Instance<ResourceData> configurations;

    private SctConfigurationImpl sctConfig;

    public SctConfigurationEnricher() {
    }

    @NotManagedByContainer
    public SctConfigurationEnricher(Instance<ResourceData> configurations) {
        this.configurations = configurations;
    }

    public void init(@Observes BeforeSuite before) {
        this.sctConfig = new SctConfigurationImpl();
        SctConfigurator.getInstance().setConfiguration(sctConfig);
    }

    public void beforeTest(@Observes Before before) throws MalformedURLException {
        ResourceBusinessNaming naming =
                new ResourceBusinessNaming(before.getTestClass(), before.getTestMethod());

        String businessKey = naming.create();
        initBoundary(this.sctConfig);

        setMockingBoundary(businessKey, this.sctConfig);
        setRecordingBoundary(businessKey, this.sctConfig);
    }

    public void afterTest(@Observes After after) {
        initBoundary(this.sctConfig);
    }

    private void setMockingBoundary(String id, SctConfigurationImpl exec)
            throws MalformedURLException {
        if (this.configurations.get().getMockingResourcesAsMap().containsKey(id)) {
            Resource res = this.configurations.get().getMockingResourcesAsMap().get(id);
            exec.setResponseLoading(true);

            // TODO: Not responsibility of this class -> move to resource!
            exec.setResponseLoadingUrl(new RemoteLocationResolver().resolve(res.getLocation()));
        }
    }

    private void setRecordingBoundary(String id, SctConfigurationImpl exec)
            throws MalformedURLException {
        if (this.configurations.get().getRecordingResourcesAsMap().containsKey(id)) {
            Resource res = this.configurations.get().getRecordingResourcesAsMap().get(id);
            exec.setCallRecording(true);
            exec.setCallRecordingUrl(new ClientLocationResolver().resolve(res.getLocation()));
        }
    }

    private void initBoundary(SctConfigurationImpl exec) {
        exec.setResponseLoadingUrl(null);
        exec.setCallRecordingUrl(null);
        exec.setResponseLoading(false);
        exec.setCallRecording(false);
    }

}
