//package org.sct.arquillian;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//public class TestUtils {
//
//    private static final String TEST_RES = "src/test/resources/";
//
//    public static String convertStreamToString(java.io.InputStream is) {
//        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
//        return s.hasNext() ? s.next() : "";
//    }
//
//    public static <T> T createRandom(Class<T> type) {
//        return GenericObjectMocker.SINGLETON.createMock(type);
//    }
//
//    public static File getResource(String child) {
//        File rootF = new File(".");
//        File childF = new File(rootF, child);
//        return childF;
//    }
//
//    public static URL getTestResource(String child) {
//        File rootF = new File(".");
//        if (child.startsWith("/")) {
//            throw new RuntimeException(
//                    "Test resource can not start with slash (/). Use getResource() instead.");
//        }
//        File childF = new File(rootF, TEST_RES + child);
//        try {
//            return childF.toURI().toURL();
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static URL createTempFile(String prefix, String ext) {
//        try {
//            return File.createTempFile(prefix, "." + ext).toURI().toURL();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}
