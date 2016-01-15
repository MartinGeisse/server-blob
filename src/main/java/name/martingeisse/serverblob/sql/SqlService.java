/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.sql;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import name.martingeisse.serverblob.configuration.ConfigurationStore;

/**
 * The central access point for application code that wants to access a database.
 *
 * Each configured database has a string-typed ID that is used to identify that
 * database. The ID has the same syntax as a Java identifier.
 */
public class SqlService {

	/**
	 * Constructor.
	 * @param configurationStore the configuration store
	 */
	@Inject
	public SqlService(final ConfigurationStore configurationStore) {
		
		// load configuration
		ImmutableMap<String, String> configuration = configurationStore.getConfiguration(SqlService.class);
		
		// determine database IDs
		Set<String> ids = new HashSet<>();
		for (Map.Entry<String, String> entry : configuration.entrySet()) {
			String key = entry.getKey();
			int index = key.indexOf('.');
			if (index < 0) {
				continue;
			}
			ids.add(key.substring(0, index));
		}
		
		// extract database configurations
		for (String id : ids) {
			String databaseFamilyName = configuration.get(id + ".family");
			if (databaseFamilyName == null) {
				throw new RuntimeException("missing database family property: " + id + ".family");
			}
			// TODO determine family
			SqlDatabaseFamily databaseFamily = null;
			DatabaseConfiguration databaseConfiguration = databaseFamily.newConfiguration(configuration, id);
			// TODO create connector
		}
			
	}

}
