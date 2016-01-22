/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.sql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.mysql.MySQLQuery;
import com.querydsl.sql.mysql.MySQLQueryFactory;

/**
 * MySQL-specific closeable query factory implementation.
 */
public class CloseableMySQLQueryFactory extends MySQLQueryFactory implements CloseableSqlQueryFactory<MySQLQuery<?>> {

	private static final Configuration MYSQL_QUERYDSL_CONFIGURATION = new Configuration(new MySQLTemplates());

	CloseableMySQLQueryFactory(Connection connection) {
		super(MYSQL_QUERYDSL_CONFIGURATION, () -> connection);
	}

	// override
	@Override
	public void close() throws IOException {
		try {
			getConnection().close();
		} catch (SQLException e) {
			throw new IOException("could not close SQL connection", e);
		}
	}
	
}
