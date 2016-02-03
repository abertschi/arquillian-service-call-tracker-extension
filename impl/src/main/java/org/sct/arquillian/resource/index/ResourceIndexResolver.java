package org.sct.arquillian.resource.index;

import java.util.List;

import org.sct.arquillian.resource.LocationResolver;
import org.sct.arquillian.resource.model.Resource;

/**
 * @author Andrin Bertschi
 */
interface ResourceIndexResolver {

    List<Resource> resolveIndex(String location, LocationResolver resolver);

}