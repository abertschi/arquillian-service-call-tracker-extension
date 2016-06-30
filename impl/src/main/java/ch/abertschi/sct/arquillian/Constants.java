package ch.abertschi.sct.arquillian;

public class Constants
{

    public static final String EXT_NAME = "serviceCallTracker";

    public static final String PROPERTY_ENABLED = "enabled";
    public static final String PROPERTY_SUITE_EXTENSION = "useSuiteExtension";
    public static final String PROPERTY_RECORDING_ENABLED = "recordingEnabled";
    public static final String PROPERTY_REPLAYING_ENABLED = "replayingEnabled";


    public static final String PROPERTY_RECORDING_SOURCE_TYPE = "recordingSourceType";
    public static final String PROPERTY_RECORDING_MODE = "recordingMode";

    public static final String PROPERTY_REPLAYING_SOURCE_TYPE = "replayingSourceType";
    public static final String PROPERTY_REPLAYING_SKIP_DOUBLES = "replayingSkipDoubles";

    public static final String PROPERTY_THROW_EXCEPTION_ON_NOT_FOUND = "throwExceptionOnNotFound";
    public static final String PROPERTY_THROW_EXCEPTION_ON_INCOMPATIBLE_RETURN_TYPE = "throwExceptionOnIncompatibleReturnType";

    public static final String PROPERTY_SOURCE_DIRECTORY = "sourceDirectory";
    public static final String PROPERTY_RECORDING_STORAGE_DIRECTORY = "recordingStorageDirectory";
    public static final String PROPERTY_REPLAYING_STORAGE_DIRECTORY = "replayingStorageDirectory";

    public static final String CONFIGURATION_PATH = "/META-INF/sct/";

    public static final String CONFIGURATION_FILE = CONFIGURATION_PATH + "service-call-tracker-config.xml";
}
