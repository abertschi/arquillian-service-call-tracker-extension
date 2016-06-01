//package ch.abertschi.sct.arquillian;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URISyntaxException;
//
//import org.apache.commons.io.FileUtils;
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.Archive;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import ch.abertschi.sct.api.SctConfiguration;
//import ch.abertschi.sct.api.SctConfigurator;
//import ch.abertschi.sct.arquillian.api.SctInterceptBy;
//
//@RunWith(Arquillian.class)
//public class SctInterceptByTest
//{
//
//    @Deployment
//    public static Archive<?> createArchive()
//    {
//        return ShrinkWrap.create(JavaArchive.class)
//                .addPackages(true, SctInterceptByTest.class.getPackage());
//    }
//
//    @Test
//    @SctInterceptBy("src/test/resources/files/response.xml")
//    public void test_response_loading() throws IOException, URISyntaxException
//    {
//        // given
//        String content = "response.xml";
//        SctConfiguration config = SctConfigurator.getInstance().getConfiguration();
//        // when
//        String readContent = TestUtils.convertStreamToString(config.getResponseLoadingUrl().openStream());
//
//        // than
//        Assert.assertEquals(content, readContent);
//        Assert.assertNotNull(config.getResponseLoadingUrl());
//        Assert.assertFalse(config.isCallRecording());
//        Assert.assertTrue(config.isResponseLoading());
//        Assert.assertNull(config.getCallRecordingUrl());
//    }
//
//}
