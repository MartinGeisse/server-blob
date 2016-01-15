/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.app_pages;

import com.google.common.collect.ImmutableList;
import name.martingeisse.serverblob.dependency_injection.ExtensionPoint;

/**
 * This extension point can be used to mount pages with actual application
 * functionality (as opposed to management console pages) at
 * /application/*.
 */
@ExtensionPoint
public interface ApplicationPages {

	/**
	 * Mounts application pages.
	 * 
	 * @param mounter the mounter used to actually mount pages
	 */
	public void mount(Mounter mounter);

	/**
	 * Returns a name to display application page links in the management console.
	 * 
	 * @return the name
	 */
	public String getName();

	/**
	 * Gets the references to root pages to display in the management console.
	 * 
	 * @return the root page references
	 */
	public ImmutableList<RootPageReference> getRootPageReferences();
	
}
