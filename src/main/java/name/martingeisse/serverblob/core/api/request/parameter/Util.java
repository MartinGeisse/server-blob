/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.core.api.request.parameter;


/**
 *
 */
class Util {

	public static <T> T parse(String name, String value, T onNullValue, Parser<T> parser) {
		if (value == null) {
			return onNullValue;
		}
		try {
			return parser.parse(value);
		} catch (ParserException e) {
			throw new ParameterFormatException(name);
		}
	}
	
}
