package org.sct.arquillian;

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

import org.sct.SctInterceptor;
import org.sct.api.SctConfiguration;
import org.sct.api.SctConfigurator;
import org.sct.arquillian.api.SctInterceptTo;

/**
 * Tests for basic features using annotation {@link SctInterceptTo}.
 *
 *
 */
@RunWith(Arquillian.class)
public class SctInterceptToTest {

    @Deployment
    public static Archive<?> createArchive() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "tests.jar")
                .addPackages(true, SctInterceptToTest.class.getPackage())
                .addPackage(SctInterceptor.class.getPackage());

            return ShrinkWrap.create(WebArchive.class, "test.war")
                    .addAsLibrary(jar);

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
