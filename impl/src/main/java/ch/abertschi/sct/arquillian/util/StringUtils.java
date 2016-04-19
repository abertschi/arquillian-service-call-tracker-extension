package ch.abertschi.sct.arquillian.util;

/**
 * @author Andrin Bertschi
 */
public class StringUtils {

    /**
     * @param path
     * @return Get filename of a path.
     */
    public static String extractFileName(String path) {
        String[] direcotryStrucutre = path.split("/|\\\\");
        if (direcotryStrucutre.length == 1) {
            return direcotryStrucutre[0];
        } else {
            return direcotryStrucutre[direcotryStrucutre.length - 1];
        }
    }

}
