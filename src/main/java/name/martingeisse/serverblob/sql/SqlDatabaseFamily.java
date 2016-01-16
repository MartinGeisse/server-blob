/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.sql;

import java.util.Map;
import name.martingeisse.serverblob.dependency_injection.ExtensionPoint;

/**
 * Different implementations provide support for MySQL, PostgreSQL, ...
 */
@ExtensionPoint
public interface SqlDatabaseFamily {

	/**
	 * @return the name of this family
	 */
	public String getName();
	
	/**
	 * Creates a database configuration from a configuration map by calling to appropriate constructor.
	 * 
	 * @param configuration the configuration map
	 * @param sectionName the configuration section name
	 * @return the database configuration
	 */
	public DatabaseConfiguration newConfiguration(final Map<String, String> configuration, final String sectionName);

}
