package org.sct.arquillian.resource.model;

import org.jboss.shrinkwrap.api.asset.Asset;

import org.sct.arquillian.util.StringUtils;

/**
 * @author Andrin Bertschi
 */
public class ResourceImpl implements Resource {

    private Asset asset;

    private String location;

    private String businessKey;

    @Override
    public String getBusinessKey() {
        if (this.businessKey == null || this.businessKey.isEmpty()) {
            return StringUtils.extractFileName(getLocation());
        } else {
            return this.businessKey;
        }
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    @Override
    public Asset getAsset() {
        return this.asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

}
