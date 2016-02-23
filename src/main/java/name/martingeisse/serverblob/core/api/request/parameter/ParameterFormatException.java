/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.core.api.request.parameter;

/**
 * Indicates a parameter with an invalid format.
 */
public class ParameterFormatException extends ParameterException {

	/**
	 * Constructor.
	 * @param name the parameter name
	 */
	public ParameterFormatException(final String name) {
		super(name, "invalid value");
	}

}
