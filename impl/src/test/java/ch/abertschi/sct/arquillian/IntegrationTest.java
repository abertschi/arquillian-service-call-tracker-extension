package ch.abertschi.sct.arquillian;

import java.io.File;
import java.io.IOException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.abertschi.sct.api.SctConfiguration;
import ch.abertschi.sct.api.SctConfigurator;
import ch.abertschi.sct.arquillian.api.SctInterceptTo;
import ch.abertschi.sct.arquillian.api.SctInterceptBy;

@RunWith(Arquillian.class)
public class IntegrationTest
{

    @Deployment
    public static Archive<?> createArchive() {
        return ShrinkWrap.create(JavaArchive.class, "tests.jar")
                .addPackages(true, SctInterceptToTest.class.getPackage());
    }

    @Test
    @SctInterceptTo("arquillian-rocks-new.xml")
    //@SctInterceptBy("src/test/resources/files/arquillian-rocks.xml")
    public void test_recording_and_response_loading() throws IOException {
        String stubContent = "arquillian-rocks";
        SctConfiguration c = SctConfigurator.getInstance().getConfiguration();

        Assert.assertTrue(c.isCallRecording());
        Assert.assertEquals("", TestUtils.convertStreamToString(c.getCallRecordingUrl().openStream()));

        Assert.assertTrue(c.isResponseLoading());
        Assert.assertEquals(stubContent, TestUtils.convertStreamToString(c.getResponseLoadingUrl().openStream()));
    }
}
