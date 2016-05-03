package ch.abertschi.sct.arquillian.deployment;

import org.jboss.shrinkwrap.resolver.api.maven.*;

/**
 * Created by abertschi on 28/03/16.
 */
public class ResolverUtil
{
    private static ResolverUtil instance;

    private MavenResolverSystem mResolver;

    private PomEquippedResolveStage mPomEquippedResolver;

    public static ResolverUtil get()
    {
        if (instance == null)
        {
            instance = new ResolverUtil();
        }
        return instance;
    }

    private ResolverUtil()
    {
        // TODO: Resolve via plugin not working, custom artifact repository not chosen
        if (ResolverConfig.isShrinkwrapResolveViaPlugin())
        {
            mPomEquippedResolver = Maven.configureResolver()
                    .workOffline(ResolverConfig.isMavenOffline())
                    .fromFile(ResolverConfig.getMavenExecutionGlobalSettings())
                    .offline(ResolverConfig.isMavenOffline())
                    .loadPomFromFile(ResolverConfig.getMavenExecutionPomFile());
        }
        else if (ResolverConfig.isShrinkwrapResolveViaPom())
        {
            ConfigurableMavenResolverSystem resolver = Maven.configureResolver();
            if (ResolverConfig.isMavenOffline())
            {
                resolver.workOffline();
            }
        }
        else
        {
            mResolver = Maven.resolver();
        }
    }

    public MavenStrategyStage resolve(String... resolves)
    {
        if (ResolverConfig.isShrinkwrapResolveViaPlugin()
                || ResolverConfig.isShrinkwrapResolveViaPom())
        {
            return mPomEquippedResolver.resolve(resolves);
        }
        else
        {
            return mResolver.resolve(resolves);
        }
    }
}
