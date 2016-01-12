/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.console.configuration;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.ResourceReference;
import name.martingeisse.serverblob.dependency_injection.ExtensionPoint;

/**
 * Describes a configuration page.
 */
@ExtensionPoint
public interface ConfigurationPageInfo {

	/**
	 * @return the page name
	 */
	public String getName();
	
	/**
	 * @return a resource reference for the icon to show on the main configuration page
	 */
	public ResourceReference getIconResourceReference();
	
	/**
	 * @return the page class for this page
	 */
	public Class<? extends WebPage> getPageClass();

}
