//package org.sct.arquillian;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import org.sct.api.SctConfiguration;
//import org.sct.api.SctConfigurator;
//
//public abstract class AbstractBaseIT {
//
//    protected SctConfiguration sctConfiguration;
//
//    @Before
//    public void ensure_configuration_is_available() {
//        this.sctConfiguration = SctConfigurator.getInstance().getConfiguration();
//        Assert.assertNotNull(sctConfiguration);
//    }
//
//    @Test
//    public void test_resource_initialisation() {
//        Assert.assertFalse(this.sctConfiguration.isResponseLoading());
//        Assert.assertFalse(this.sctConfiguration.isCallRecording());
//        Assert.assertNull(this.sctConfiguration.getResponseLoadingUrl());
//        Assert.assertNull(this.sctConfiguration.getCallRecordingUrl());
//    }
//}
