/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.http;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simplified request-handling interface. Basically a dumbed-down {@link HttpServlet}.
 */
public interface RequestHandler {

	/**
	 * Handles a request.
	 * 
	 * @param request the request
	 * @param response the response
	 * @throws IOException on I/O errors
	 */
	public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
}
