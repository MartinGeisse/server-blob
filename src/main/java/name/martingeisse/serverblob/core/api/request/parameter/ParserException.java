/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.core.api.request.parameter;


/**
 * Indicates that a parameter value could not be parsed. This exception
 * is converted to a {@link ParameterFormatException} outside the parser.
 */
public final class ParserException extends Exception {
}
