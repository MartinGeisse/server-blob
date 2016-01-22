/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.sql;

import java.io.Closeable;
import com.querydsl.sql.SQLCommonQuery;
import com.querydsl.sql.SQLCommonQueryFactory;
import com.querydsl.sql.dml.SQLDeleteClause;
import com.querydsl.sql.dml.SQLInsertClause;
import com.querydsl.sql.dml.SQLMergeClause;
import com.querydsl.sql.dml.SQLUpdateClause;

/**
 * Base type for a {@link SQLCommonQueryFactory} that is also {@link Closeable}.
 *
 * @param <Q> the query type
 */
public interface CloseableSqlQueryFactory<Q extends SQLCommonQuery<?>> extends SQLCommonQueryFactory<Q, SQLDeleteClause, SQLUpdateClause, SQLInsertClause, SQLMergeClause>, Closeable {
}
