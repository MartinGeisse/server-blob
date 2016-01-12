/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.dependency_injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate an interface with this annotation to turn it into an extension point.
 * 
 * Get all extensions for an extension point T by declaring an injected constructor
 * parameter of type Set&lt;T&gt;.
 * 
 * TODO an alternative to extension points + extensions is to define pluggable Guice
 * modules. At some time this should be evaluated.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtensionPoint {
}
