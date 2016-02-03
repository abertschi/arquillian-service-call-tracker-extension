package org.sct.arquillian.resource;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.sct.arquillian.util.exception.AsctException;

/**
 * @author Andrin Bertschi
 */
public interface LocationResolver {

    URL resolve(String location);

    public static class ClientLocationResolver implements LocationResolver {

        @Override
        public URL resolve(String location) {
            try {
                return new File(location).toURI().toURL();
            } catch (MalformedURLException e) {
              throw new AsctException(e);
            }
        }
    }

    public static class RemoteLocationResolver implements LocationResolver {

        @Override
        public URL resolve(String location) {
            return Thread.currentThread().getContextClassLoader().getResource(location);
        }

    }
}
