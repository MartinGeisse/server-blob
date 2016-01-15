/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.app_pages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import name.martingeisse.serverblob.console.AbstractDefaultLayoutPage;
import name.martingeisse.serverblob.dependency_injection.DependencyListModel;

/**
 * Shows links to the "root" application pages.
 */
public class ApplicationPagesReferencePage extends AbstractDefaultLayoutPage {

	/**
	 * Constructor.
	 */
	public ApplicationPagesReferencePage() {
		getLayoutBorder().add(new ListView<ApplicationPages>("sections", new DependencyListModel<>(ApplicationPages.class)) {
			@Override
			protected void populateItem(ListItem<ApplicationPages> item) {
				item.add(new Label("name", new PropertyModel<>(item.getModel(), "name")));
				item.add(new ListView<RootPageReference>("pages", new PropertyModel<>(item.getModel(), "rootPageReferences")) {
					@Override
					protected void populateItem(ListItem<RootPageReference> item) {
						RootPageReference reference = item.getModelObject();
						BookmarkablePageLink<Void> link = new BookmarkablePageLink<>("link", reference.getPageClass(), reference.getParameters());
						link.add(new Label("name", item.getModelObject().getName()));
						item.add(link);
					}
				});
			}
		});
	}

}
