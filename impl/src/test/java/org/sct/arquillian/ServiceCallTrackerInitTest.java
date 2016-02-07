package org.sct.arquillian;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.sct.api.SctConfiguration;
import org.sct.api.SctConfigurator;

@RunWith(Arquillian.class)
public class ServiceCallTrackerInitTest {

    protected SctConfiguration sctConfiguration;

    @Deployment
    public static Archive<?> createArchive() {
        return ShrinkWrap.create(JavaArchive.class, "tests.jar")
                .addPackages(true, ServiceCallTrackerInitTest.class.getPackage());
    }

    @Before
    public void ensure_configuration_is_available() {
        this.sctConfiguration = SctConfigurator.getInstance().getConfiguration();
        Assert.assertNotNull(sctConfiguration);
    }

    @Test
    public void test_resource_initialisation() {
        Assert.assertFalse(this.sctConfiguration.isResponseLoading());
        Assert.assertFalse(this.sctConfiguration.isCallRecording());
        Assert.assertNull(this.sctConfiguration.getResponseLoadingUrl());
        Assert.assertNull(this.sctConfiguration.getCallRecordingUrl());
    }
}
