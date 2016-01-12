/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.latex;

import name.martingeisse.serverblob.dependency_injection.Extension;
import name.martingeisse.serverblob.http.VirtualHostInfo;
import name.martingeisse.serverblob.http.VirtualHostProvider;

/**
 *
 */
@Extension
public class LatexVirtualHostProvider implements VirtualHostProvider {

	// override
	@Override
	public VirtualHostInfo getVirtualHost(final String hostName) {
		if (hostName.equals("latex")) {
			return new VirtualHostInfo((request, response) -> {
				response.getWriter().println("LaTeX API");
			});
		}
		return null;
	}

}
