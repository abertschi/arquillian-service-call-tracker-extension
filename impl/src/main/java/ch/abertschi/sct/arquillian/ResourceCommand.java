package ch.abertschi.sct.arquillian;


import org.jboss.arquillian.container.test.impl.client.deployment.command.AbstractCommand;

/**
 * Created by abertschi on 07/02/16.
 */
public class ResourceCommand extends AbstractCommand<Boolean>
{
    private String content;

    private String path;

    private String name;

    private boolean append = false;

    public ResourceCommand(String name, String path, String content)
    {
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

    public boolean isAppend()
    {
        return append;
    }

    public ResourceCommand setAppend(boolean append)
    {
        this.append = append;
        return this;
    }
}
