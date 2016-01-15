/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.dependency_injection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import org.apache.wicket.model.AbstractReadOnlyModel;
import name.martingeisse.serverblob.console.wicket.MyWicketApplication;

/**
 * Model for a dependency list.
 * 
 * This model requires a comparator to determine the list order. If
 * no comparator is specified, it will use a default comparator that
 * sorts by implementation class, but will fail with an exception if
 * two implementations with the same class are found. This is because
 * the only alternative would be to leave the list unsorted, which may
 * produce a different order each time and cause wicket to route events
 * to the wrong component, e.g. making the user "click" a button he never
 * actually clicked.
 * 
 * @param <T> the dependency type
 */
public class DependencyListModel<T> extends AbstractReadOnlyModel<List<T>> {

	private static final Comparator<Object> DEFAULT_COMPARATPOR = (x, y) -> x.getClass().getCanonicalName().compareTo(y.getClass().getCanonicalName());
	
	private final Class<T> type;
	private final Comparator<? super T> comparator;

	/**
	 * Constructor.
	 * @param type the dependency type
	 */
	public DependencyListModel(final Class<T> type) {
		this(type, DEFAULT_COMPARATPOR);
	}

	/**
	 * Constructor.
	 * @param type the dependency type
	 * @param comparator the comparator that determines the list order
	 */
	public DependencyListModel(final Class<T> type, final Comparator<? super T> comparator) {
		this.type = type;
		this.comparator = comparator;
	}

	// override
	@Override
	public List<T> getObject() {
		Set<T> dependencySet = MyWicketApplication.get().getDependencies(type);
		List<T> dependencyList = new ArrayList<T>(dependencySet);
		Collections.sort(dependencyList, comparator);
		return dependencyList;
	}

}
