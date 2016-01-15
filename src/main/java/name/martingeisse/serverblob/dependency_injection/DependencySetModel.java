/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.dependency_injection;

import java.util.Set;
import org.apache.wicket.model.AbstractReadOnlyModel;
import name.martingeisse.serverblob.console.wicket.MyWicketApplication;

/**
 * Model for a dependency set.
 * 
 * @param <T> the dependency type
 */
public class DependencySetModel<T> extends AbstractReadOnlyModel<Set<T>> {

	private final Class<T> type;

	/**
	 * Constructor.
	 * @param type the dependency type
	 */
	public DependencySetModel(final Class<T> type) {
		this.type = type;
	}

	// override
	@Override
	public Set<T> getObject() {
		return MyWicketApplication.get().getDependencies(type);
	}

}
