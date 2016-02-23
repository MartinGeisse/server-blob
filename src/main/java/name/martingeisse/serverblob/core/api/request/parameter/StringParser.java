/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.core.api.request.parameter;

/**
 * This parser just returns the parsed text.
 */
public final class StringParser implements Parser<String> {

	// override
	@Override
	public String parse(final String s) {
		return s;
	}

}
