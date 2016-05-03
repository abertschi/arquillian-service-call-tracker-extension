package ch.abertschi.sct.arquillian.deployment;

/**
 * Created by abertschi on 17/04/16.
 */
public class ResolverConfig
{
    public static final String MAVEN_OFFLINE = "org.apache.maven.offline";
    public static final String SHRINKWRAP_RESOLVE_VIA_PLUGIN = "shrinkwrap.resolve-via-plugin";
    public static final String SHRINKWRAP_RESOLVE_VIA_POM = "shrinkwrap.resolve-via-pom";
    public static final String MAVEN_EXECUTION_POM_FILE = "maven.execution.pom-file";
    public static final String MAVEN_EXECUTION_GLOBAL_SETTINGS = "maven.execution.global-settings";
    public static final String MAVEN_EXECUTIONL_USER_SETTINGS = "maven.execution.user-settings";

    public static void setMavenOffline(boolean offline)
    {
        System.setProperty(MAVEN_OFFLINE, offline ? "true" : "false");
    }

    public static boolean isMavenOffline()
    {
        return !System.getProperty(MAVEN_OFFLINE, "false").equals("false");
    }

    public static void setShrinkwrapResolveViaPlugin(boolean resolveViaPlugin)
    {
        System.setProperty(SHRINKWRAP_RESOLVE_VIA_PLUGIN, resolveViaPlugin ? "true" : "false");
    }

    public static boolean isShrinkwrapResolveViaPlugin()
    {
        return !System.getProperty(SHRINKWRAP_RESOLVE_VIA_PLUGIN, "false").equals("false");
    }

    public static void setShrinkwrapResolveViaPom(boolean resolveViaPom)
    {
        System.setProperty(SHRINKWRAP_RESOLVE_VIA_POM, resolveViaPom ? "true" : "false");
    }

    public static boolean isShrinkwrapResolveViaPom()
    {
        return !System.getProperty(SHRINKWRAP_RESOLVE_VIA_POM, "false").equals("false");
    }

    public static String getMavenExecutionPomFile()
    {
        return System.getProperty(MAVEN_EXECUTION_POM_FILE, "pom.xml");
    }

    public static String getMavenExecutionGlobalSettings()
    {
        return System.getProperty(MAVEN_EXECUTION_GLOBAL_SETTINGS);
    }

    public static String getMavenExecutionUserSettings()
    {
        return System.getProperty(MAVEN_EXECUTIONL_USER_SETTINGS);
    }
}
