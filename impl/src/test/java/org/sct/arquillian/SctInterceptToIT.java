//package org.sct.arquillian;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.Archive;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;
//import org.jboss.shrinkwrap.resolver.api.maven.Maven;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import SctInterceptTo;
//
///**
// * Tests for basic features using annotation {@link SctInterceptTo}.
// *
// * 
// */
//@RunWith(Arquillian.class)
//public class SctInterceptToIT extends AbstractBaseIT {
//
//    @Deployment
//    public static Archive<?> createArchive() {
//        return Deployments.GET.createForClass(SctInterceptToIT.class);
//    }
//
//    // ---------------------------------------------------------------------------
//    // tests
//    // ---------------------------------------------------------------------------
//
//    @Test
//    @SctInterceptTo("test_record_data_configured.xml")
//    public void test_recording() throws IOException {
//        String fileContent = TestUtils.convertStreamToString(this.sctConfiguration
//                .getCallRecordingUrl().openStream());
//
//        Assert.assertTrue(this.sctConfiguration.isCallRecording());
//        Assert.assertTrue(!this.sctConfiguration.getCallRecordingUrl().toString().isEmpty());
//        Assert.assertTrue(fileContent.isEmpty());
//        Assert.assertFalse(this.sctConfiguration.isResponseLoading());
//        Assert.assertNull(this.sctConfiguration.getResponseLoadingUrl());
//    }
//}
