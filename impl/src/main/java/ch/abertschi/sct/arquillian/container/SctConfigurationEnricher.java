//package ch.abertschi.sct.arquillian.container;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URISyntaxException;
//
//import org.jboss.arquillian.container.test.spi.command.CommandService;
//import org.jboss.arquillian.core.api.Instance;
//import org.jboss.arquillian.core.api.annotation.Inject;
//import org.jboss.arquillian.core.api.annotation.Observes;
//import org.jboss.arquillian.test.spi.event.suite.AfterSuite;
//import org.jboss.arquillian.test.spi.event.suite.Before;
//import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;
//
//import ch.abertschi.sct.api.SctConfigurator;
//import ch.abertschi.sct.arquillian.ResourceCommand;
//import ch.abertschi.sct.arquillian.resource.naming.ResourceNaming;
//import ch.abertschi.sct.arquillian.resource.model.Resource;
//import ch.abertschi.sct.arquillian.util.exception.AsctException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Sets service call tracker configuration for current test execution.
// *
// * @author Andrin Bertschi
// */
//public class SctConfigurationEnricher
//{
//    @Inject
//    Instance<ResourceData> configurations;
//
//    @Inject
//    Instance<CommandService> commandService;
//
//    private static final Logger LOG = LoggerFactory.getLogger(SctConfigurationEnricher.class);
//
//    private SctConfigurationImpl sctConfig;
//
//    void init(@Observes BeforeSuite before)
//    {
//        this.sctConfig = new SctConfigurationImpl();
//        SctConfigurator.getInstance().setConfiguration(sctConfig);
//    }
//
//    void beforeTest(@Observes Before before) throws MalformedURLException
//    {
//        ResourceNaming naming =
//                new ResourceNaming(before.getTestClass(), before.getTestMethod());
//
//        String businessKey = naming.create();
//        initServiceCallTracker(this.sctConfig);
//
//        setupReplaying(businessKey, this.sctConfig);
//        setupRecording(businessKey, this.sctConfig);
//    }
//
//    void afterTest(@Observes AfterSuite after) throws URISyntaxException, IOException
//    {
//        for (Resource r : configurations.get().getRecordingResources())
//        {
//            Resource rOnClient = configurations.get().getRecordingResoucesOnClientAsMap().get(r.getName());
//            String content = convertStreamToString(r.getAsset().openStream());
//            if (content != null && !content.isEmpty())
//            {
//                /*
//                 * TODO: Suite Extension compatibility hack
//                 * AfterSuite event is fired several times although
//                 * all data is within the same suite.
//                 * Send only those recordings which contain data in order not to overwrite already written ones.
//                 */
//                commandService.get().execute(new ResourceCommand(r.getName(), rOnClient.getPath(), content));
//            }
//        }
//        initServiceCallTracker(this.sctConfig);
//    }
//
//    private void setupReplaying(String id, SctConfigurationImpl exec)
//            throws MalformedURLException
//    {
//        if (this.configurations.get().getReplayingResourcesAsMap().containsKey(id))
//        {
//            Resource res = this.configurations.get().getReplayingResourcesAsMap().get(id);
//            exec.setResponseLoading(true);
//            exec.setResponseLoadingUrl(Thread.currentThread().getContextClassLoader().getResource(res.getPath()));
//        }
//    }
//
//    private void setupRecording(String id, SctConfigurationImpl exec)
//            throws MalformedURLException
//    {
//        if (this.configurations.get().getRecordingResourcesAsMap().containsKey(id))
//        {
//            Resource res = this.configurations.get().getRecordingResourcesAsMap().get(id);
//            exec.setCallRecording(true);
//            try
//            {
//                exec.setCallRecordingUrl(new File(res.getPath()).toURI().toURL());
//            }
//            catch (MalformedURLException e)
//            {
//                throw new AsctException(e);
//            }
//        }
//    }
//
//    private void initServiceCallTracker(SctConfigurationImpl exec)
//    {
//        exec.setResponseLoadingUrl(null);
//        exec.setCallRecordingUrl(null);
//        exec.setResponseLoading(false);
//        exec.setCallRecording(false);
//    }
//
//    static String convertStreamToString(java.io.InputStream is)
//    {
//        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
//        return s.hasNext() ? s.next() : "";
//    }
//
//}
