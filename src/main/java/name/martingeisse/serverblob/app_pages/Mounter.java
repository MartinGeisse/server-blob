/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.app_pages;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Used by an {@link ApplicationPages} implementation to actually mount pages.
 */
public interface Mounter {

	/**
	 * Mounts a page. This is equivalent to {@link WebApplication#mountPage(String, Class)},
	 * except that it mounts pages at the /application path.
	 * 
	 * @param path the path relative to /application
	 * @param pageClass the page class to mount
	 */
	public <T extends Page> void mount(String path, Class<T> pageClass);
	
}
