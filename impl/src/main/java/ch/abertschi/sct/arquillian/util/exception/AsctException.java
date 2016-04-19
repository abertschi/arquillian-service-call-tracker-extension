package ch.abertschi.sct.arquillian.util.exception;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Asct Default exception.
 */
public class AsctException extends RuntimeException implements ErrorContext {

    private static final long serialVersionUID = 1L;

    private static final String SEPARATOR = "\n-------------------------------";

    private Map<String, List<String>> errorStuff = new HashMap<String, List<String>>();

    public AsctException(String message, Throwable t) {
        super(message, t);
        if (message != null) {
            add("message", message);
        }
        if (t != null) {
            add("cause-exception", t.getClass().getName());
            add("cause-message", t instanceof AsctException ? ((AsctException) t).getShortMessage() : t.getMessage());
        }
    }

    public AsctException(Exception e) {
        super(null, e);
    }

    public AsctException(String s) {
        super(s, null);
    }

    @Override
    public void add(String name, String information) {
        List<String> list = null;
        if (errorStuff.get(name) == null) {
            list = new LinkedList<String>();
            errorStuff.put(name, list);
            
        }
        else {
            list = errorStuff.get(name);
        }
        list.add(information);
    }

    @Override
    public void set(String name, String information) {
        List<String> list = new LinkedList<String>();
        list.add(information);
        errorStuff.put(name, list);
    }

    @Override
    public String get(String errorKey) {
        return getAsSingleString(errorStuff.get(errorKey));
    }
    
    private String getAsSingleString(List<String> list) {
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            build.append(list.get(i));
            build.append("[").append(i).append("]").append(";");
        }
        return build.toString();
    }

    @Override
    public Iterator<String> keys() {
        return errorStuff.keySet().iterator();
    }

    @Override
    public String getMessage() {
        StringBuffer result = new StringBuffer();
        if (super.getMessage() != null) {
            result.append(super.getMessage());
        }
        if (!result.toString().endsWith(SEPARATOR)) {
            result.append("\n---- Asct debugging information ----");
        }
        for (Iterator<String> iterator = keys(); iterator.hasNext();) {
            String k = (String) iterator.next();
            String v = get(k);
            result.append('\n').append(k);
            result.append("                    ".substring(Math.min(20, k.length())));
            result.append(": ").append(v);
        }
        result.append(SEPARATOR);
        return result.toString();
    }
    
    public String getShortMessage() {
        return super.getMessage();
    }
}
