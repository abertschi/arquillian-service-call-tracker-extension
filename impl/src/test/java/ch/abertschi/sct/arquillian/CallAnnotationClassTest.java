package ch.abertschi.sct.arquillian;

import ch.abertschi.sct.api.Configuration;
import ch.abertschi.sct.api.SctConfigurator;
import ch.abertschi.sct.arquillian.api.RecordCall;
import ch.abertschi.sct.arquillian.api.ReplayCall;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by abertschi on 29/06/16.
 */
@RunWith(Arquillian.class)
@RecordCall
public class CallAnnotationClassTest
{
    @Deployment
    public static Archive deploy()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(CallAnnotationClassTest.class);
    }

    @Test
    public void record_call_class_annotation()
    {
        Configuration config = SctConfigurator.getInstance().getConfiguration();
        Assert.assertTrue(config.isRecordingEnabled());
        Assert.assertFalse(config.isReplayingEnabled());
    }


}
