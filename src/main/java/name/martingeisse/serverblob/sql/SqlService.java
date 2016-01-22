/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import name.martingeisse.serverblob.configuration.ConfigurationStore;

/**
 * The central access point for application code that wants to access a database.
 *
 * Each configured database has a string-typed ID that is used to identify that
 * database. The ID has the same syntax as a Java identifier.
 */
public class SqlService {

	private final Map<String, DatabaseConfiguration> configurations = new HashMap<>();

	/**
	 * Constructor.
	 * @param families the database families
	 * @param configurationStore the configuration store
	 */
	@Inject
	public SqlService(final Set<SqlDatabaseFamily> families, final ConfigurationStore configurationStore) {

		// map database families by name
		final Map<String, SqlDatabaseFamily> familiesByName = families.stream().collect(Collectors.toMap(SqlDatabaseFamily::getName, Function.identity()));

		// load configuration
		final ImmutableMap<String, String> configuration = configurationStore.getConfiguration(SqlService.class);

		// determine database IDs
		final Set<String> ids = new HashSet<>();
		for (final Map.Entry<String, String> entry : configuration.entrySet()) {
			final String key = entry.getKey();
			final int index = key.indexOf('.');
			if (index < 0) {
				continue;
			}
			ids.add(key.substring(0, index));
		}

		// extract database configurations
		for (final String id : ids) {
			final String databaseFamilyName = configuration.get(id + ".family");
			if (databaseFamilyName == null) {
				throw new RuntimeException("missing database family property: " + id + ".family");
			}
			final SqlDatabaseFamily databaseFamily = familiesByName.get(databaseFamilyName);
			if (databaseFamily == null) {
				throw new RuntimeException("invalid SQL database family name: " + databaseFamilyName);
			}
			final DatabaseConfiguration databaseConfiguration = databaseFamily.newConfiguration(configuration, id);
			configurations.put(id, databaseConfiguration);
		}

	}

	/**
	 * @return all database IDs
	 */
	public ImmutableSet<String> getDatabaseIds() {
		return ImmutableSet.copyOf(configurations.keySet());
	}

	/**
	 * @param databaseId the database ID
	 * @return the configuration, or null if unknown
	 */
	public DatabaseConfiguration getDatabaseConfiguration(String databaseId) {
		return configurations.get(databaseId);
	}
	
	/**
	 * Creates a new JDBC connection. The caller is responsible for closing the connection.
	 * 
	 * @param databaseId the database ID
	 * @return a new database connection
	 * @throws SQLException on errors
	 */
	public Connection createConnection(String databaseId) throws SQLException {
		DatabaseConfiguration configuration = configurations.get(databaseId);
		if (configuration == null) {
			throw new IllegalArgumentException("invalid database ID: " + databaseId);
		}
		return configuration.createConnection();
	}

	/**
	 * Creates a QueryDSL query factory. The caller is responsible for closing the query factory.
	 * 
	 * @param databaseId the database ID
	 * @return a new query factory
	 * @throws SQLException on errors
	 */
	public CloseableSqlQueryFactory<?> createQueryFactory(String databaseId) throws SQLException {
		DatabaseConfiguration configuration = configurations.get(databaseId);
		if (configuration == null) {
			throw new IllegalArgumentException("invalid database ID: " + databaseId);
		}
		return configuration.createQueryFactory();
	}

}
