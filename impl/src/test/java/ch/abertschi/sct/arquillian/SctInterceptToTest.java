package ch.abertschi.sct.arquillian;

import java.io.IOException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.abertschi.sct.SctInterceptor;
import ch.abertschi.sct.api.SctConfiguration;
import ch.abertschi.sct.api.SctConfigurator;
import ch.abertschi.sct.arquillian.api.SctInterceptTo;

/**
 * Tests for basic features using annotation {@link SctInterceptTo}.
 *
 *
 */
@RunWith(Arquillian.class)
public class SctInterceptToTest {

    @Deployment
    public static Archive<?> createArchive() {
        return ShrinkWrap.create(JavaArchive.class, "tests.jar")
                .addPackages(true, SctInterceptToTest.class.getPackage());
    }

    // ---------------------------------------------------------------------------
    // tests
    // ---------------------------------------------------------------------------

    @Test
    @SctInterceptTo("test.xml")
    public void test_recording() throws IOException {
        System.out.println("fine");

    }
}
