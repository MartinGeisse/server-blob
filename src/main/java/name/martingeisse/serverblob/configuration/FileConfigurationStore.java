/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.google.common.collect.ImmutableMap;

/**
 * A file-based configuration store. The files are similar to Java .properties files,
 * except they're UTF-8 encoded. For this reason, they are called .configuration
 * and not .properties to avoid confusion.
 */
public class FileConfigurationStore implements ConfigurationStore {

	private final File folder;
	private final Map<Class<?>, ImmutableMap<String, String>> cache = new HashMap<>();

	/**
	 * Constructor.
	 * @param folder the folder where configuration is stored
	 */
	public FileConfigurationStore(File folder) {
		this.folder = folder;
	}

	// override
	@Override
	public ImmutableMap<String, String> getConfiguration(final Class<?> key) {
		ImmutableMap<String, String> configuration = cache.get(key);
		if (configuration == null) {
			final Properties properties = new Properties();
			try {
				try (FileInputStream in = new FileInputStream(getFile(key))) {
					try (InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
						properties.load(reader);
					}
				}
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
			@SuppressWarnings("unchecked")
			final Map<String, String> map = (Map<String, String>)(Map<?, ?>)properties;
			configuration = ImmutableMap.copyOf(map);
			cache.put(key, configuration);
		}
		return configuration;
	}

	// override
	@Override
	public void setConfiguration(final Class<?> key, final ImmutableMap<String, String> configuration) {
		cache.put(key, configuration);
		final Properties properties = new Properties();
		properties.putAll(configuration);
		try {
			try (FileOutputStream out = new FileOutputStream(getFile(key))) {
				try (OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
					properties.store(writer, null);
				}
			}
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	// override
	@Override
	public void clearCache() {
		cache.clear();
	}

	private File getFile(final Class<?> key) {
		return new File(folder, key.getCanonicalName() + ".configuration");
	}

}
