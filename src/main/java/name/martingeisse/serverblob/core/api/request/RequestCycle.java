/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.core.api.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import name.martingeisse.serverblob.core.api.request.parameter.Parameters;

/**
 * This is an abstraction to deal with the request and response. The most
 * important implementation wraps the {@link HttpServletRequest} and
 * {@link HttpServletResponse}.
 */
public interface RequestCycle {

	/**
	 * @return the request method
	 */
	public RequestMethod getRequestMethod();

	/**
	 * @return the all-lowercase textual representation of the request method
	 */
	public String getLowercaseRequestMethodText();

	/**
	 * @return the path segment queue
	 */
	public PathSegmentQueue getPathSegmentQueue();

	/**
	 * @return the querystring parameters
	 */
	public Parameters getQuerystringParameters();

}
