package ch.abertschi.sct.arquillian.util.exception;

import java.util.Iterator;

public interface ErrorContext {

    /**
     * Add some information to the error message. 
     */
    void add(String name, String information);

    /**
     * Set some information to the error message. If the identifier is already in use, the new
     * information will replace the old one.
     */
    void set(String name, String information);

    /**
     * Retrieve information of the error message.
     */
    String get(String errorKey);

    /**
     * Retrieve an iterator over all keys of the error message.
     */
    Iterator<String>  keys();

}
