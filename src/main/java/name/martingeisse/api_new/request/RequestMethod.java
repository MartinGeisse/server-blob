/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.api_new.request;


/**
 * Enumerates HTTP request methods.
 */
public enum RequestMethod {

	/**
	 *
	 */
	GET,
	
	/**
	 *
	 */
	PUT,
	
	/**
	 *
	 */
	POST,
	
	/**
	 *
	 */
	DELETE,
	
	/**
	 *
	 */
	HEAD,
	
	/**
	 *
	 */
	OPTIONS;
	
	/**
	 * Tries to parse the request method from its textual representation.
	 * 
	 * @param methodText the textual representation of the method
	 * @return the request method, or null if not recognized
	 */
	public RequestMethod parse(String methodText) {
		try {
			return valueOf(methodText);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	
}
