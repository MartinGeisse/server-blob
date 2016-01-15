/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.app_pages;

import org.apache.wicket.Page;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Points to a "root" page of the application pages. This generates a corresponding
 * link on the "application pages" section of the management console.
 */
public final class RootPageReference {

	private final String name;
	private final Class<? extends Page> pageClass;
	private final PageParameters parameters;

	/**
	 * Constructor.
	 *
	 * @param name the name of this reference
	 * @param pageClass the page class
	 */
	public RootPageReference(final String name, final Class<? extends Page> pageClass) {
		this(name, pageClass, null);
	}

	/**
	 * Constructor.
	 * @param name the name of this reference
	 * @param pageClass the page class
	 * @param parameters the page parameters (null if none)
	 */
	public RootPageReference(final String name, final Class<? extends Page> pageClass, final PageParameters parameters) {
		this.name = name;
		this.pageClass = pageClass;
		this.parameters = (parameters == null ? null : new PageParameters(parameters));
	}

	/**
	 * Getter method for the name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter method for the pageClass.
	 * @return the pageClass
	 */
	public Class<? extends Page> getPageClass() {
		return pageClass;
	}

	/**
	 * Getter method for the parameters.
	 * @return the parameters
	 */
	public PageParameters getParameters() {
		return parameters;
	}

}
