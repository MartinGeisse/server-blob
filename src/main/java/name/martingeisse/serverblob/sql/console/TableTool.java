/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.sql.console;

import name.martingeisse.serverblob.dependency_injection.ExtensionPoint;

/**
 * Generates a tool link for a database table.
 */
@ExtensionPoint
public interface TableTool {

	/**
	 * Decides whether this tool can be used for a table.
	 * 
	 * @param databaseId the database id
	 * @param tableName the name of the table
	 * @return whether the tool can be used for that table
	 */
	default public boolean appliesTo(String databaseId, String tableName) {
		return true;
	}
	
	/**
	 * @return the displayed name
	 */
	public String getDisplayName();

	/**
	 * Invokes this tool. This method gets called in an AJAX context.
	 * 
	 * @param databaseId the database id
	 * @param tableName the name of the table
	 */
	public void invoke(String databaseId, String tableName);
	
}
