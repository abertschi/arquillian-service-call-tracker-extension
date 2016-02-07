package org.sct.arquillian.client;

import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.TestClass;
import org.sct.arquillian.ResourceCommand;

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
    public void receiveRessource(@Observes ResourceCommand command) throws IOException
    {
        command.setResult(Boolean.TRUE);
        File f = new File(command.getPath());
        f.getParentFile().mkdirs();
        if (f.exists())
        {
            f.delete();
        }
        Files.write(Paths.get(f.toURI()), command.getContent().getBytes(), StandardOpenOption.CREATE_NEW);
    }
}
