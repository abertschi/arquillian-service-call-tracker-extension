package org.sct.arquillian.resource.model;

import org.jboss.shrinkwrap.api.asset.Asset;

import org.sct.arquillian.util.StringUtils;

/**
 * @author Andrin Bertschi
 */
public class ResourceImpl implements Resource {

    private Asset asset;

    private String location;

    private String name;

    @Override
    public String getName() {
        if (this.name == null || this.name.isEmpty()) {
            return StringUtils.extractFileName(getPath());
        } else {
            return this.name;
        }
    }

    @Override
    public String getPath() {
        return this.location;
    }

    @Override
    public Asset getAsset() {
        return this.asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public void setPath(String location) {
        this.location = location;
    }

    public void setName(String businessKey) {
        this.name = businessKey;
    }

}
