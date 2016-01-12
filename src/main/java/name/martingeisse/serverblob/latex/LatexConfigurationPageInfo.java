/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.latex;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import name.martingeisse.serverblob.console.configuration.ConfigurationPageInfo;
import name.martingeisse.serverblob.dependency_injection.Extension;

/**
 *
 */
@Extension
public class LatexConfigurationPageInfo implements ConfigurationPageInfo {

	// override
	@Override
	public String getName() {
		return "LaTeX";
	}

	
	// override
	@Override
	public ResourceReference getIconResourceReference() {
		return new PackageResourceReference(LatexConfigurationPage.class, "latex-logo.png");
	}


	// override
	@Override
	public Class<? extends WebPage> getPageClass() {
		return LatexConfigurationPage.class;
	}

}
