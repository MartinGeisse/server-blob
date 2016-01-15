/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.app_pages;

import org.apache.wicket.markup.html.WebPage;
import name.martingeisse.serverblob.console.HomePageSection;
import name.martingeisse.serverblob.dependency_injection.Extension;

/**
 *
 */
@Extension
public final class ApplicationPagesHomePageSection implements HomePageSection {

	// override
	@Override
	public String getName() {
		return "Application Pages";
	}
	
	// override
	@Override
	public Class<? extends WebPage> getPageClass() {
		return ApplicationPagesReferencePage.class;
	}

}
