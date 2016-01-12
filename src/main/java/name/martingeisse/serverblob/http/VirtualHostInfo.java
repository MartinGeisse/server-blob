/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.http;

/**
 *
 */
public final class VirtualHostInfo {

	private final RequestHandler requestHandler;

	/**
	 * Constructor.
	 * @param requestHandler the request handler
	 */
	public VirtualHostInfo(final RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	/**
	 * Getter method for the requestHandler.
	 * @return the requestHandler
	 */
	public RequestHandler getRequestHandler() {
		return requestHandler;
	}
	
}
