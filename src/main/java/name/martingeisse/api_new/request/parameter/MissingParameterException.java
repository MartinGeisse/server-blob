/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.api_new.request.parameter;

/**
 * Indicates a missing parameter.
 */
public class MissingParameterException extends ParameterException {

	/**
	 * Constructor.
	 * @param name the parameter name
	 */
	public MissingParameterException(String name) {
		super(name, "parameter is missing");
	}
	
}
