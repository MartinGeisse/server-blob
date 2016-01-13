/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.configuration;

import com.google.common.collect.ImmutableMap;

/**
 * This object stores the configuration for plugins.
 * 
 * Configuration is divided into sections, each using an arbitrary class as its key.
 * A section typically corresponds to a plugin.
 * 
 * Configuration data may be cached in a write-through manner by this class to improve
 * performance. Use the {@link #clearCache()} method if the actual store data has changed.
 * Write-back caching is disallowed because configuration changes are important enough to
 * persist them immediately.
 */
public interface ConfigurationStore {

	/**
	 * Returns a configuration section.
	 * 
	 * @param key the section key
	 * @return the configuration
	 */
	public ImmutableMap<String, String> getConfiguration(Class<?> key);
	
	/**
	 * Stores a configuration section.
	 * 
	 * @param key the section key
	 * @param configuration the configuration
	 */
	public void setConfiguration(Class<?> key, ImmutableMap<String, String> configuration);
	
	/**
	 * Clears any cached configuration data. This is useful if the configuration has
	 * been changed in the configuration store by other means.
	 */
	public void clearCache();
	
}
