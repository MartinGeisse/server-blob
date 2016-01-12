/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.console;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import name.martingeisse.serverblob.console.wicket.MyWicketApplication;

/**
 * Implements the standard layout of the management console.
 */
public class DefaultLayoutBorder extends Border {

	/**
	 * Constructor.
	 * @param id the wicket id
	 */
	public DefaultLayoutBorder(String id) {
		super(id);
		IModel<List<HomePageSection>> sectionListModel = new AbstractReadOnlyModel<List<HomePageSection>>() {
			@Override
			public List<HomePageSection> getObject() {
				List<HomePageSection> list = new ArrayList<>(MyWicketApplication.get().getDependencies(HomePageSection.class));
				Comparator<HomePageSection> comparator = (section1, section2) -> {
					String name1 = section1.getName(), name2 = section2.getName();
					int nameOrder = name1.compareTo(name2);
					if (nameOrder == 0) {
						return 0;
					}
					if (section1 instanceof DashboardHomePageSection) {
						return -1;
					}
					if (section2 instanceof DashboardHomePageSection) {
						return 1;
					}
					return nameOrder;
				};
				Collections.sort(list, comparator);
				return list;
			}
		};
		addToBorder(new ListView<HomePageSection>("sectionButtons", sectionListModel) {
			@Override
			protected void populateItem(ListItem<HomePageSection> item) {
				BookmarkablePageLink<?> link = new BookmarkablePageLink<Void>("sectionButton", item.getModelObject().getPageClass());
				link.add(item.getModelObject().newButtonContentComponent("buttonContents"));
				item.add(link);
			}
		});
		addToBorder(new Link<Void>("logoutButton") {
			@Override
			public void onClick() {
				// TODO
			}
		});
	}
	
}
