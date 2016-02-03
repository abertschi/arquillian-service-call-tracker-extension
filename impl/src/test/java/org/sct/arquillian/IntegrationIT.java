//package org.sct.arquillian;
//
//import java.io.File;
//import java.io.IOException;
//
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.Archive;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import org.sct.api.SctConfiguration;
//import org.sct.api.SctConfigurator;
//import org.sct.arquillian.api.SctInterceptTo;
//import org.sct.arquillian.api.SctInterceptBy;
//
///**
// * Test for common features.
// * @author C925004
// *
// */
//@RunWith(Arquillian.class)
//public class IntegrationIT {
//
//    protected SctConfigurator sctConfiguration;
//
//    @Deployment
//    public static Archive<?> createArchive() {
//        return Deployments.GET.createForClass(IntegrationIT.class);
//    }
//
//    @Test
//    @SctInterceptTo("new-file-1.xml")
//    @SctInterceptBy("arquillian-rocks.xml")
//    public void test_recording_and_response_loading() throws IOException {
//        ensureEnvironment();
//
//        // given
//        File base = new File(".");
//        final String recordingLocation = "src/test/resources/service-call-mock/datasets/recording/new-file-1.xml";
//        File recordingRes = new File(base, recordingLocation);
//        if (recordingRes.exists()) {
//            recordingRes.delete();
//        }
//        Assert.assertTrue(!recordingRes.exists());
//        String contentOfMockingFile = "arquillian-rocks";
//
//        // then
//        Assert.assertTrue(this.sctConfiguration.isCallRecording());
//        Assert.assertEquals("", TestUtils.convertStreamToString(this.sctConfiguration.getCallRecordingUrl().openStream()));
//
//        Assert.assertTrue(this.sctConfiguration.isResponseLoading());
//        Assert.assertEquals(contentOfMockingFile, TestUtils.convertStreamToString(this.sctConfiguration.getResponseLoadingUrl().openStream()));
//    }
//
//    private void ensureEnvironment() {
//        this.sctConfiguration = SctConfigurator.getInstance().getConfiguration();
//        Assert.assertNotNull(sctConfiguration);
//    }
//}
