/**
 * Copyright (c) 2010 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.tools;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import com.querydsl.codegen.BeanSerializer;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.codegen.MetaDataExporter;
import com.querydsl.sql.codegen.MetaDataSerializer;


/**
 * Generates QueryDSL classes.
 */
public class QueryDslCodeGeneratorMain {

	/**
	 * The main method.
	 * @param args ...
	 * @throws Exception on errors
	 */
	public static void main(String[] args) throws Exception {
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/gargl?zeroDateTimeBehavior=convertToNull&useTimezone=false&characterEncoding=utf8&characterSetResults=utf8", "root", "")) {
			MetaDataExporter exporter = new MetaDataExporter();
			exporter.setTargetFolder(new File("src/generated/java"));
			exporter.setPackageName("name.martingeisse.serverblob.dbtables");
			exporter.setSerializerClass(MetaDataSerializer.class);
			exporter.setBeanSerializer(new BeanSerializer());
			// exporter.setConfiguration(new CustomMysqlQuerydslConfiguration(new MySQLTemplates(), DateTimeZone.forID("Europe/Berlin")));
			exporter.setConfiguration(new Configuration(new MySQLTemplates()));
			exporter.export(connection.getMetaData());
		}
	}
	
}
