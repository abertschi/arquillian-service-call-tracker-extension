package org.sct.arquillian.container;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Server side extension loader.
 * 
 * @author Andrin Bertschi
 */
public class AsctRemoteExtension implements RemoteLoadableExtension {
    
    private static final Logger LOG = LoggerFactory.getLogger(AsctRemoteExtension.class);

    @Override
    public void register(ExtensionBuilder builder) {
        LOG.debug("Registering Asct remote loadable extensions");
        
        builder.observer(SctConfigurationEnricher.class)
                .observer(RemoteResourceProcessor.class);
    }
}
