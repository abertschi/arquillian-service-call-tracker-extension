package org.sct.arquillian.resource.model;

import org.jboss.shrinkwrap.api.asset.Asset;

/**
 * @author Andrin Bertschi
 */
public interface Resource {

    String getBusinessKey();

    String getLocation();

    Asset getAsset();

}
