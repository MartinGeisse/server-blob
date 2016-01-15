/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.console.configuration;

import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import name.martingeisse.serverblob.console.AbstractDefaultLayoutPage;
import name.martingeisse.serverblob.dependency_injection.DependencyListModel;

/**
 *
 */
public class ConfigurationMainPage extends AbstractDefaultLayoutPage {

	/**
	 * Constructor.
	 */
	public ConfigurationMainPage() {
		final IModel<List<ConfigurationPageInfo>> infosModel = new DependencyListModel<>(ConfigurationPageInfo.class, (x, y) -> x.getName().compareTo(y.getName()));
		getLayoutBorder().add(new ListView<ConfigurationPageInfo>("links", infosModel) {
			@Override
			protected void populateItem(ListItem<ConfigurationPageInfo> item) {
				Link<Void> link = new BookmarkablePageLink<>("link", item.getModelObject().getPageClass());
				link.add(new Image("icon", item.getModelObject().getIconResourceReference()));
				link.add(new Label("caption", new PropertyModel<>(item.getModelObject(), "name")));
				item.add(link);
			}
		});
	}

}
