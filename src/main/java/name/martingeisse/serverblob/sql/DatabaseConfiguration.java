/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Base class for the configuration of a specific database.
 *
 * Subclasses should be implemented as (immutable) value-type classes.
 */
public abstract class DatabaseConfiguration {

	private final String displayName;
	private final String hostName;
	private final Integer explicitPort;
	private final String databaseName;
	private final String schemaName;
	private final String userName;
	private final String password;

	/**
	 * Constructor.
	 * @param configuration the configuration
	 * @param sectionName the name of the configuration section
	 */
	public DatabaseConfiguration(final Map<String, String> configuration, final String sectionName) {
		this.displayName = getRequired(configuration, sectionName + ".displayName");
		this.hostName = getRequired(configuration, sectionName + ".hostName");
		this.explicitPort = getOptionalInteger(configuration, sectionName + ".explicitPort");
		this.databaseName = getRequired(configuration, sectionName + ".databaseName");
		this.schemaName = configuration.get(sectionName + ".schemaName");
		this.userName = getRequired(configuration, sectionName + ".userName");
		this.password = getRequired(configuration, sectionName + ".password");
	}

	private static String getRequired(final Map<String, String> configuration, final String key) {
		final String value = configuration.get(key);
		if (value == null) {
			throw new RuntimeException("missing configuration key: " + key);
		}
		return value;
	}

	private static Integer getOptionalInteger(final Map<String, String> configuration, final String key) {
		final String value = configuration.get(key);
		if (value == null) {
			return null;
		}
		try {
			return new Integer(value);
		} catch (final NumberFormatException e) {
			throw new RuntimeException("expected integer value for configuration key " + key);
		}
	}

	/**
	 * Getter method for the displayName.
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Getter method for the hostName.
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * Getter method for the explicitPort.
	 * @return the explicitPort
	 */
	public Integer getExplicitPort() {
		return explicitPort;
	}

	/**
	 * Getter method for the databaseName.
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * Getter method for the schemaName.
	 * @return the schemaName
	 */
	public String getSchemaName() {
		return schemaName;
	}

	/**
	 * Getter method for the userName.
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Getter method for the password.
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Creates a new JDBC connection. The caller is responsible for closing the connection.
	 * 
	 * @return a new database connection
	 * @throws SQLException on errors
	 */
	public abstract Connection createConnection() throws SQLException;

	/**
	 * Creates a QueryDSL query factory. The caller is responsible for closing the query factory.
	 * 
	 * @return a new query factory
	 * @throws SQLException on errors
	 */
	public abstract CloseableSqlQueryFactory<?> createQueryFactory() throws SQLException;

}
