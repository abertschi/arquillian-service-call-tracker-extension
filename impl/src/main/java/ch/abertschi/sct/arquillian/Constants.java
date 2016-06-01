package ch.abertschi.sct.arquillian;

public class Constants
{

    public static final String EXT_NAME = "service-call-tracker";

    public static final String EXT_PROPERTY_RECORDING_ROOT = "asctRecordingHome";

    public static final String EXT_PROPERTY_MOCKING_ROOT = "asctLoadingHome";

    public static final String EXT_PROPERTY_EXCEPTION_ON_NOT_FOUND = "throwExceptionOnNotFound";

    public static final String EXT_PROPERTY_THROW_EXCEPTION_ON_WRONG_TYPE = "throwExceptionOnIncompatibleReturnType";

    public static final String RESOURCE_ROOT = "/META-INF/sct/";

    public static final String CONFIGURATION_FILE = RESOURCE_ROOT + "service-call-tracker-config.xml";

}