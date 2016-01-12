/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.http;

import name.martingeisse.serverblob.dependency_injection.ExtensionPoint;

/**
 * Maps host names to virtual hosts.
 */
@ExtensionPoint
public interface VirtualHostProvider {

	/**
	 * @param hostName the host name (taken from the "Host" HTTP header)
	 * @return the virtual host info, or null if this host is unknown
	 */
	public VirtualHostInfo getVirtualHost(String hostName);
	
}
