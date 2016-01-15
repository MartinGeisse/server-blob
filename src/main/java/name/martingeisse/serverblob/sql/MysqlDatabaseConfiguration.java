/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.sql;

import java.util.Map;

/**
 * MySQL-specific database configuration.
 */
public class MysqlDatabaseConfiguration extends DatabaseConfiguration {

	/**
	 * Constructor.
	 * @param configuration the configuration
	 * @param sectionName the name of the configuration section
	 */
	public MysqlDatabaseConfiguration(final Map<String, String> configuration, final String sectionName) {
		super(configuration, sectionName);
	}

}
