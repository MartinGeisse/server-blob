/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.console;

import org.apache.wicket.markup.html.WebPage;
import name.martingeisse.serverblob.dependency_injection.Extension;

/**
 *
 */
@Extension
public final class DashboardHomePageSection implements HomePageSection {

	// override
	@Override
	public String getName() {
		return "Home";
	}

	// override
	@Override
	public Class<? extends WebPage> getPageClass() {
		return HomePage.class;
	}
	
}
