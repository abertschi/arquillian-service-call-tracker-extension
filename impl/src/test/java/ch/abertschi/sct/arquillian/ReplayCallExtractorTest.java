package ch.abertschi.sct.arquillian;

import ch.abertschi.sct.api.Configuration;
import ch.abertschi.sct.arquillian.api.ReplayCall;
import ch.abertschi.sct.arquillian.client.ReplayCallExtractor;
import org.jboss.arquillian.test.spi.TestClass;
import org.junit.Test;

import java.io.File;

/**
 * Created by abertschi on 01/06/16.
 */
@ReplayCall(
        value = "storage.xml",
        enabled = false,
        sourceType = Configuration.INPUT_SOURCE.SINGLE_FILE,
        throwExceptionOnIncompatibleReturnType = false,
        throwExceptionOnNotFound = true)
public class ReplayCallExtractorTest
{
    @Test
    public void test_happy_extraction()
    {
        ReplayCallExtractor extractor = new ReplayCallExtractor(new File("./src/test/java"), new File("."));
        System.out.println(extractor.extractClassConfiguration(new TestClass(ReplayCallExtractorTest.class)).getPath());
        System.out.println(new File(".").getAbsolutePath());
    }
}
