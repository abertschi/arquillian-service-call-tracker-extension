package org.sct.arquillian;

/**
 * Asct constants.
 */
public class AsctConstants {
    
    /**
     * specified in arquillian.xml
     */
    public static final String EXT_NAME = "asct";
    
    public static final String EXT_PROPERTY_RECORDING_ROOT = "asctRecordingHome";
    
    public static final String EXT_PROPERTY_MOCKING_ROOT = "asctLoadingHome";

    public static final String RESOURCE_ROOT = "/META-INF/asct/";
    
    public static final String RESOURCE_MOCKING_INDEX = RESOURCE_ROOT + "asct-loading-index.properties";
    
    public static final String RESOURCE_RECORDING_INDEX = RESOURCE_ROOT + "asct-recording-index.properties";
    
}
