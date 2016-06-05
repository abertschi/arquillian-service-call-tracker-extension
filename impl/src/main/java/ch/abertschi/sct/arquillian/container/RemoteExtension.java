//package ch.abertschi.sct.arquillian.container;
//
//import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
//import org.jboss.arquillian.container.test.spi.command.Command;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Server side extension loader.
// *
// * @author Andrin Bertschi
// */
//public class RemoteExtension implements RemoteLoadableExtension
//{
//
//    @Override
//    public void register(ExtensionBuilder builder)
//    {
//        builder.observer(SctConfigurationEnricher.class)
//                .observer(RemoteResourceProcessor.class)
//                .observer(CommandServiceProducer.class);
//    }
//}
