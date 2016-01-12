/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.console;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;
import name.martingeisse.serverblob.dependency_injection.ExtensionPoint;

/**
 * Describes a section in the home page navigation.
 * 
 * TODO is not actually a "home page section" but a "navigation section" because
 * it's visible on *every* page.
 * 
 * TODO this class throws not-serializable errors. DO NOT MAKE IT SERIALIAZBLE
 * unless there's a reason -- fix the place where something tries to serialize it.
 * It shouldn't be necessary.
 */
@ExtensionPoint
public interface HomePageSection {

	/**
	 * @return the section name
	 */
	public String getName();
	
	/**
	 * @return the page class for the main page of this section
	 */
	public Class<? extends WebPage> getPageClass();

	/**
	 * @param id the wicket id
	 * @return a new component to attach to an empty SPAN tag. This is used for the navigation button.
	 */
	default public Component newButtonContentComponent(String id) {
		return new Label(id, new PropertyModel<>(this, "name"));
	}
	
}
