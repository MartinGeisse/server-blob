/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.metric;


/**
 * A metric of type T.
 * 
 * Each metric has a value of its type at each point in time. Typical use cases
 * are to record these values and/or trigger an alarm if a condition based on
 * the metric's value is true.
 * 
 * This interface only defines a method to get the metric's current value. Subtypes
 * may be able to return previous values without explicitly recording them.
 * 
 * Unless specified otherwise, a metric only works correctly when registered with
 * the {@link MetricService}. This interface does not specify whether the metric
 * can be registered with the MetricService multiple times using different IDs.
 * 
 * @param <T> the measured value type
 */
public interface Metric<T> {

	/**
	 * @return this metric's current value
	 */
	public T getValue();
	
	/**
	 * @param context the context
	 */
	public void onRegister(MetricContext context);

	/**
	 * @param context the context
	 */
	public void onUnregister(MetricContext context);

}
