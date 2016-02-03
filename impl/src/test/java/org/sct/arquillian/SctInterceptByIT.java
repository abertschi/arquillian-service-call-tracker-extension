//package org.sct.arquillian;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.Archive;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import org.sct.api.SctConfiguration;
//import SctInterceptBy;
//
///**
// * Tests of basic features using annotation {@link SctInterceptBy}.
// *
// *
// */
//@RunWith(Arquillian.class)
//public class SctInterceptByIT extends AbstractBaseIT{
//
//    @Deployment
//    public static Archive<?> createArchive() {
//        return Deployments.GET.createForClass(SctInterceptByIT.class);
//    }
//    
//
//    // ---------------------------------------------------------------------------
//    // tests
//    // ---------------------------------------------------------------------------
//
//    /**
//     * When: Mocking dataset is specified using Annotation {@link @SctInterceptBy}.
//     * Then: Dataset is available in interface {@link SctConfiguration} and correctly packed to archive, deployed and unpacked.
//     */
//    @Test
//    @SctInterceptBy("test_mock_data_configured.xml")
//    public void test_response_loading() throws IOException, URISyntaxException {
//        // given
//        String mockingFileContent = "This content is inside file test_mock_data_configured.xml";
//        
//        // when
//        String fileContent = TestUtils.convertStreamToString(this.sctConfiguration.getResponseLoadingUrl().openStream());
//        
//        // than
//        Assert.assertEquals(mockingFileContent, fileContent);
//        Assert.assertNotNull(this.sctConfiguration.getResponseLoadingUrl());
//        Assert.assertTrue(this.sctConfiguration.isCallRecording());
//        Assert.assertFalse(this.sctConfiguration.isResponseLoading());
//        Assert.assertNull(this.sctConfiguration.getCallRecordingUrl());
//    }
//    
//}
