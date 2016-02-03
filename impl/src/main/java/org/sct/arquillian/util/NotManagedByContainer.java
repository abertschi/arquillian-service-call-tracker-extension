package org.sct.arquillian.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marker annotation noting that the annotated component is not managed by inversion-of-control.
 * Operations annotated by this marker should only be called for test purposes.
 * 
 * @author Andrin Bertschi
 */
@Retention(RetentionPolicy.SOURCE)
public @interface NotManagedByContainer {

}
