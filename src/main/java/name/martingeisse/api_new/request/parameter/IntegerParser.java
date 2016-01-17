/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.api_new.request.parameter;

/**
 * This parser parses integer values.
 */
public class IntegerParser implements Parser<Integer> {

	// override
	@Override
	public Integer parse(final String s) throws ParserException {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new ParserException();
		}
	}

}
