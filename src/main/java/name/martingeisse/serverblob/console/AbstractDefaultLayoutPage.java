/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.console;

import name.martingeisse.serverblob.console.wicket.page.AbstractPage;

/**
 * Extend this page instead of {@link AbstractPage} to get a {@link DefaultLayoutBorder}
 * for free. Just wrap the page contents in wicket:extend.
 */
public abstract class AbstractDefaultLayoutPage extends AbstractPage {

	/**
	 * Constructor.
	 */
	public AbstractDefaultLayoutPage() {
		add(new DefaultLayoutBorder("defaultLayoutBorder"));
	}

	/**
	 * @return the layout border
	 */
	public final DefaultLayoutBorder getLayoutBorder() {
		return (DefaultLayoutBorder)get("defaultLayoutBorder");
	}
	
}
