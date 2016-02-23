/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.core.api.request.parameter;

/**
 * This interface represents querystring or x-www-form-urlencoded parameters.
 * 
 * In general, this interface can represent any kind of parameters with string-typed
 * keys and values.
 */
public interface Parameters {

	/**
	 * @param name the parameter name
	 * @return the specified querystring parameter value, or null if the
	 * parameter is not present
	 */
	public String getOptionalQuerystringParameter(String name);
	
	/**
	 * @param name the parameter name
	 * @param defaultValue the default value
	 * @return the specified querystring parameter value, or the default
	 * value if the parameter is not present
	 */
	default public String getOptionalQuerystringParameter(String name, String defaultValue) {
		String value = getOptionalQuerystringParameter(name);
		return (value == null ? defaultValue : value);
	}
	
	/**
	 * @param name the parameter name
	 * @return the specified querystring parameter value
	 */
	default public String getRequiredQuerystringParameter(String name) {
		String value = getOptionalQuerystringParameter(name);
		if (value == null) {
			throw new MissingParameterException(name);
		}
		return value;
	}
	
	/**
	 * @param name the parameter name
	 * @param parser the parameter parser
	 * @return the specified querystring parameter value, or null if the
	 * parameter is not present
	 */
	default public <T> T getOptionalQuerystringParameter(String name, Parser<T> parser) {
		String value = getOptionalQuerystringParameter(name);
		return Util.parse(name, value, null, parser);
	}
	
	/**
	 * @param name the parameter name
	 * @param defaultValue the default value
	 * @param parser the parameter parser
	 * @return the specified querystring parameter value, or the default
	 * value if the parameter is not present
	 */
	default public <T> T getOptionalQuerystringParameter(String name, String defaultValue, Parser<T> parser) {
		String value = getOptionalQuerystringParameter(name, defaultValue);
		return Util.parse(name, value, null, parser);
	}
	
	/**
	 * @param name the parameter name
	 * @param parser the parameter parser
	 * @param defaultValue the default value
	 * @return the specified querystring parameter value, or the default
	 * value if the parameter is not present
	 */
	default public <T> T getOptionalQuerystringParameter(String name, Parser<T> parser, T defaultValue) {
		String value = getOptionalQuerystringParameter(name);
		return Util.parse(name, value, defaultValue, parser);
	}
	
	/**
	 * @param name the parameter name
	 * @param parser the parameter parser
	 * @return the specified querystring parameter value
	 */
	default public <T> T getRequiredQuerystringParameter(String name, Parser<T> parser) {
		try {
			return parser.parse(getRequiredQuerystringParameter(name));
		} catch (ParserException e) {
			throw new ParameterFormatException(name);
		}
	}

}
