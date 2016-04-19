package ch.abertschi.sct.arquillian.resource.index;

import java.util.List;

import ch.abertschi.sct.arquillian.resource.model.Resource;

/**
 * @author Andrin Bertschi
 */
interface ResourceIndexBuilder
{

    Resource createIndex(String location, List<Resource> resources);

}
