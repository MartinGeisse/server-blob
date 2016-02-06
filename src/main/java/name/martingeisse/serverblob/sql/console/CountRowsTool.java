/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.sql.console;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import com.google.inject.Inject;
import name.martingeisse.serverblob.dependency_injection.Extension;
import name.martingeisse.serverblob.sql.SqlService;
import name.martingeisse.serverblob.util.AjaxRequestUtil;

/**
 * This tool counts the rows in a table.
 */
@Extension
public final class CountRowsTool implements TableTool {

	private final SqlService sqlService;
	
	/**
	 * Constructor.
	 * @param sqlService (injected)
	 */
	@Inject
	public CountRowsTool(SqlService sqlService) {
		this.sqlService = sqlService;
	}

	// override
	@Override
	public String getDisplayName() {
		return "count";
	}

	// override
	@Override
	public void invoke(String databaseId, String tableName) {
		try (Connection connection = sqlService.createConnection(databaseId)) {
			try (Statement statement = connection.createStatement()) {
				try (ResultSet resultSet = statement.executeQuery("select count(*) from " + tableName)) {
					resultSet.next();
					AjaxRequestUtil.getAjaxRequestTarget().appendJavaScript("alert('" + resultSet.getLong(1) + "');");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
