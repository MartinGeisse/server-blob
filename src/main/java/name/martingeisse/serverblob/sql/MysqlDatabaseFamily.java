/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.sql;

import java.util.Map;
import name.martingeisse.serverblob.dependency_injection.Extension;

/**
 * This is the entry point for MySQL support.
 */
@Extension
public class MysqlDatabaseFamily implements SqlDatabaseFamily {

	// override
	@Override
	public DatabaseConfiguration newConfiguration(final Map<String, String> configuration, final String sectionName) {
		return new MysqlDatabaseConfiguration(configuration, sectionName);
	}

}
