/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * MySQL-specific database configuration.
 */
public class MysqlDatabaseConfiguration extends DatabaseConfiguration {

	//
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private final String url;

	/**
	 * Constructor.
	 * @param configuration the configuration
	 * @param sectionName the name of the configuration section
	 */
	public MysqlDatabaseConfiguration(final Map<String, String> configuration, final String sectionName) {
		super(configuration, sectionName);
		StringBuilder builder = new StringBuilder("jdbc:mysql://");
		// TODO port
		builder.append(getHostName());
		builder.append('/');
		builder.append(getDatabaseName());
		// TODO schema
		builder.append("?zeroDateTimeBehavior=convertToNull&useTimezone=false&characterEncoding=utf8&characterSetResults=utf8");
		url = builder.toString();
	}

	// override
	@Override
	public Connection createConnection() throws SQLException {
		return DriverManager.getConnection(url, getUserName(), getPassword());
	}

}
