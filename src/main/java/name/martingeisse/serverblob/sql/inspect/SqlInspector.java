/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.sql.inspect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableSortedSet;
import com.google.inject.Inject;
import name.martingeisse.serverblob.sql.SqlService;

/**
 * This service collects information about the structure of connected SQL databases.
 */
public class SqlInspector {

	private final LoadingCache<String, ImmutableSortedSet<String>> tableNameCache;
	
	/**
	 * Constructor.
	 * @param sqlService (injected)
	 */
	@Inject
	public SqlInspector(SqlService sqlService) {
		this.tableNameCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build(new CacheLoader<String, ImmutableSortedSet<String>>() {
			@Override
			public ImmutableSortedSet<String> load(String databaseId) throws Exception {
				SortedSet<String> result = new TreeSet<>();
				try (Connection connection = sqlService.createConnection(databaseId)) {
					ResultSet resultSet = connection.getMetaData().getTables(null, null, "%", null);
					while (resultSet.next()) {
						result.add(resultSet.getString(3));
					}
				}
				return ImmutableSortedSet.copyOf(result);
			}
		});
	}

	/**
	 * Fetches table names for the specified database ID. Returned data is cached for 10 second.
	 * 
	 * @param databaseId the database ID
	 * @return the table names, in an alphabetically sorted set 
	 */
	public ImmutableSortedSet<String> fetchTableNames(String databaseId) {
		try {
			return tableNameCache.get(databaseId);
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
	
}
