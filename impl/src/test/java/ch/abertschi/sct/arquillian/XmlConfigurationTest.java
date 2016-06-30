package ch.abertschi.sct.arquillian;

import ch.abertschi.sct.arquillian.api.RecordCall;
import ch.abertschi.sct.arquillian.api.ReplayCall;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

/**
 * Created by abertschi on 29/06/16.
 */
@RunWith(Arquillian.class)
public class XmlConfigurationTest
{
    @Deployment
    public static Archive deploy()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(XmlConfigurationTest.class.getPackage());
               // .addAsResource("arquillian-configuration-test.xml", "arquillian.xml");
    }

    @Test
    @RecordCall
    @ReplayCall("storage.xml")
    public void storage()
    {
        System.out.println("test");
    }

}
