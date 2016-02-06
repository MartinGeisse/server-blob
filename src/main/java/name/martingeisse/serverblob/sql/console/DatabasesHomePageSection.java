/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.sql.console;

import org.apache.wicket.markup.html.WebPage;
import name.martingeisse.serverblob.console.HomePageSection;
import name.martingeisse.serverblob.dependency_injection.Extension;

/**
 *
 */
@Extension
public final class DatabasesHomePageSection implements HomePageSection {

	// override
	@Override
	public String getName() {
		return "Databases";
	}
	
	// override
	@Override
	public Class<? extends WebPage> getPageClass() {
		return DatabasesPage.class;
	}

}
