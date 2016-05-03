package ch.abertschi.sct.arquillian.client;

import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.TestClass;
import ch.abertschi.sct.arquillian.ResourceCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by abertschi on 07/02/16.
 */
public class ResourceCommandReceiver
{
    private static final Logger LOG = LoggerFactory.getLogger(ResourceCommandReceiver.class);

    void receiveRessource(@Observes ResourceCommand command) throws IOException
    {
        command.setResult(Boolean.TRUE);
        File f = new File(command.getPath());
        f.getParentFile().mkdirs();
        if (f.exists())
        {
            f.delete();
        }
        LOG.info("Writing recording '%s' to %s", command.getName(), command.getPath());
        Files.write(Paths.get(f.toURI()), command.getContent().getBytes(), StandardOpenOption.CREATE_NEW);
    }
}
