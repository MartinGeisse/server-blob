/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.sql.console;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import name.martingeisse.serverblob.console.AbstractDefaultLayoutPage;
import name.martingeisse.serverblob.console.wicket.MyWicketApplication;
import name.martingeisse.serverblob.sql.DatabaseConfiguration;
import name.martingeisse.serverblob.sql.SqlService;
import name.martingeisse.serverblob.sql.inspect.SqlInspector;
import name.martingeisse.serverblob.util.MappedComparator;

/**
 *
 */
public class DatabasesPage extends AbstractDefaultLayoutPage {

	/**
	 * Constructor.
	 */
	public DatabasesPage() {
		SqlService sqlService = MyWicketApplication.get().getDependency(SqlService.class);
		IModel<List<String>> databaseIdListModel = new AbstractReadOnlyModel<List<String>>() {
			@Override
			public List<String> getObject() {
				List<String> list = new ArrayList<>(sqlService.getDatabaseIds());
				Collections.sort(list, MappedComparator.of(id -> sqlService.getDatabaseConfiguration(id).getDisplayName()));
				return list;
			}
		};
		getLayoutBorder().add(new ListView<String>("databases", databaseIdListModel) {
			@Override
			protected void populateItem(ListItem<String> item) {
				IModel<DatabaseConfiguration> configurationModel = new AbstractReadOnlyModel<DatabaseConfiguration>() {
					@Override
					public DatabaseConfiguration getObject() {
						return sqlService.getDatabaseConfiguration(item.getModelObject());
					};
				};
				
				item.add(new Label("displayName", new PropertyModel<>(configurationModel, "displayName")));
				item.add(new Label("id", item.getModel()));
				IModel<List<String>> tableNamesModel = new AbstractReadOnlyModel<List<String>>() {
					@Override
					public List<String> getObject() {
						return new ArrayList<>(MyWicketApplication.get().getDependency(SqlInspector.class).fetchTableNames(item.getModelObject()));
					};
				};
				item.add(new ListView<String>("tables", tableNamesModel) {
					@Override
					protected void populateItem(ListItem<String> item) {
						item.add(new Label("name", item.getModel()));
					};
				});
			};
		});
	}
	
}
