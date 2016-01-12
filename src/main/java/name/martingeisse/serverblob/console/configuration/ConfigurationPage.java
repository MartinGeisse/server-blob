/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.console.configuration;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import name.martingeisse.serverblob.console.AbstractDefaultLayoutPage;

/**
 *
 */
public abstract class ConfigurationPage extends AbstractDefaultLayoutPage {

	
	/**
	 * Constructor.
	 */
	public ConfigurationPage() {
		getLayoutBorder().add(new BookmarkablePageLink<Void>("backLink", ConfigurationMainPage.class));
		getLayoutBorder().add(new Label("pageTitle", new PropertyModel<>(this, "title")));
	}
	
	/**
	 * @return the title of this page (for the headline / breadcrumbs)
	 */
	public abstract String getTitle();
	
}
