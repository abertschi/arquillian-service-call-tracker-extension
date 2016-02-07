package org.sct.arquillian;

import java.io.IOException;
import java.net.URISyntaxException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.sct.api.SctConfiguration;
import org.sct.api.SctConfigurator;
import org.sct.arquillian.api.SctInterceptBy;

@RunWith(Arquillian.class)
public class SctInterceptByTest
{

    @Deployment
    public static Archive<?> createArchive() {
        return ShrinkWrap.create(JavaArchive.class, "tests.jar")
                .addPackages(true, SctInterceptByTest.class.getPackage());
    }

    @Test
    @SctInterceptBy("src/test/resources/files/test_mock_data_configured.xml")
    public void test_response_loading() throws IOException, URISyntaxException {
        // given
        String mockingFileContent = "This content is inside file test_mock_data_configured.xml";
        SctConfiguration config = SctConfigurator.getInstance().getConfiguration();

        // when
        String fileContent = TestUtils.convertStreamToString(config.getResponseLoadingUrl().openStream());

        // than
        Assert.assertEquals(mockingFileContent, fileContent);
        Assert.assertNotNull(config.getResponseLoadingUrl());
        Assert.assertFalse(config.isCallRecording());
        Assert.assertTrue(config.isResponseLoading());
        Assert.assertNull(config.getCallRecordingUrl());
    }

}
