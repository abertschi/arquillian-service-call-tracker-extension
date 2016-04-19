package ch.abertschi.sct.arquillian.resource;

import java.util.ArrayList;
import java.util.List;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.container.LibraryContainer;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import ch.abertschi.sct.arquillian.resource.model.Resource;
import ch.abertschi.sct.arquillian.resource.model.ResourceImpl;
import ch.abertschi.sct.arquillian.util.StringUtils;

/**
 * @author Andrin Bertschi
 */
public class ResourcePackager
{
    public void addResourcesToArchive(Archive<?> archive, List<Resource> resources)
    {
        for (Resource res : resources)
        {
            addResourceToArchive(archive, res);
        }
    }

    public void addResourceToArchive(Archive<?> archive, Resource resources)
    {
        archive.add(resources.getAsset(), resources.getPath());
    }

    public List<Resource> moveResources(String targetBaseDir, List<Resource> sourceResources)
    {
        List<Resource> targetResources = new ArrayList<>();
        for (Resource r : sourceResources)
        {
            ResourceImpl newR = new ResourceImpl();
            newR.setAsset(r.getAsset());
            newR.setName(r.getName());
            String fileName = StringUtils.extractFileName(r.getPath());
            if (!targetBaseDir.endsWith("/"))
            {
                fileName = "/" + fileName;
            }
            newR.setPath(targetBaseDir + fileName);
            targetResources.add(newR);
        }
        return targetResources;
    }

    public void mergeArchives(final Archive<?> applicationArchive, final JavaArchive dataArchive)
    {
        if (JavaArchive.class.isInstance(applicationArchive))
        {
            applicationArchive.merge(dataArchive);
        }
        else
        {
            final LibraryContainer<?> libraryContainer = (LibraryContainer<?>) applicationArchive;
            libraryContainer.addAsLibrary(dataArchive);
        }
    }
}
