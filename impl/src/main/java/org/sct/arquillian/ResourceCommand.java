package org.sct.arquillian;


import org.jboss.arquillian.container.test.impl.client.deployment.command.AbstractCommand;
import org.sct.arquillian.resource.model.Resource;

/**
 * Created by abertschi on 07/02/16.
 */
public class ResourceCommand extends AbstractCommand<Boolean>
{

    public ResourceCommand(String name, String path, String content) {
        this.name = name;
        this.content = content;
        this.path = path;
    }
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    private String name;

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    private String content;

    private String path;
}
