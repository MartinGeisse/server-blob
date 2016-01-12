/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.metric;


/**
 * The technical implementation behind a metric. A concrete metric is defined
 * using an engine and a configuration.
 * 
 * @param <T> the measured value type
 */
public interface MetricEngine<T> {

	/**
	 * Creates a metric using this engine and its default configuration.
	 * 
	 * @return the metric
	 */
	public Metric<T> createMetric();
	
}
