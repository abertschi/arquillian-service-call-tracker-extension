package ch.abertschi.sct.arquillian.deployment;

/**
 * Created by abertschi on 17/04/16.
 */
public class ResolverConfig
{
    public static final String MAVEN_OFFLINE = "org.apache.maven.offline";
    public static final String SHRINKWRAP_RESOLVE_VIA_PLUGIN = "shrinkwrap.resolve-via-plugin";
    public static final String SHRINKWRAP_RESOLVE_VIA_POM = "shrinkwrap.resolve-via-pom";

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
}
