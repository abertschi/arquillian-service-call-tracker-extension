//package org.sct.arquillian;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;
//import org.jboss.shrinkwrap.resolver.api.maven.Maven;
//
//public enum Deployments {
//
//    GET;
//
//    private static String SETTINGS;
//
//    static {
//        String set = System.getProperty("asct.maven.config");
//        if (set == null) {
//            set = "C:/eplatform/tools/maven/3/conf/settings.xml";
//            System.out.println("WARN: asct.maven.config not defined. Using " + set + " instead.");
//        }
//        SETTINGS = set;
//    }
//
//    private Deployments() {
//    }
//
//    public EnterpriseArchive createForClass(Class<?> clazz) {
//        List<JavaArchive> libs = Arrays.asList(Maven
//                .configureResolver().fromFile(SETTINGS)
//                .offline()
//                .loadPomFromFile("pom.xml")
//                .importTestDependencies()
//                .resolve()
//                .withTransitivity()
//                .as(JavaArchive.class));
//
//        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "tests.jar")
//                .addPackages(true, clazz.getPackage());
//
//        return ShrinkWrap
//                .create(EnterpriseArchive.class, "test.ear")
//                .addAsLibraries(jar)
//                .addAsLibraries(libs);
//    }
//}
