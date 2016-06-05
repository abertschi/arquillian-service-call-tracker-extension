package ch.abertschi.sct.arquillian;

import ch.abertschi.sct.arquillian.api.RecordCall;
import ch.abertschi.sct.arquillian.api.ReplayCall;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by abertschi on 04/06/16.
 */
@RunWith(Arquillian.class)
public class LocalArchiveProcessorTest
{

    @Deployment
    public static Archive<?> deploy()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(LocalArchiveProcessorTest.class.getPackage());
    }

    @Test
    @ReplayCall("storage.xml")
    public void test_recording()
    {
        System.out.println("Hello");
    }
}
