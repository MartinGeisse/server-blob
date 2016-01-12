/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.metric;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;

/**
 * Central service that maintains and updates metrics. Each metric is stored
 * in this service using a string-typed ID.
 */
public class MetricService {

	private final Map<String, Context> contexts = new HashMap<>();

	/**
	 * Constructor.
	 */
	@Inject
	public MetricService() {
	}
	
	/**
	 * @return all IDs
	 */
	public synchronized final ImmutableSet<String> getAllIds() {
		return ImmutableSet.copyOf(contexts.keySet());
	}
	
	/**
	 * Gets a metric by id.
	 * 
	 * @param id the id
	 * @return the metric
	 */
	public synchronized final Metric<?> getMetric(String id) {
		return contexts.get(id).metric;
	}

	/**
	 * @return all metrics in a map, indexed by id
	 */
	public synchronized final ImmutableMap<String, Metric<?>> getMetricsById() {
		Map<String, Metric<?>> result = new HashMap<>();
		for (Map.Entry<String, Context> entry : contexts.entrySet()) {
			result.put(entry.getKey(), entry.getValue().metric);
		}
		return ImmutableMap.copyOf(result);
	}

	/**
	 * @return all metrics in a collection
	 */
	public synchronized final Collection<Metric<?>> getMetrics() {
		List<Metric<?>> result = new ArrayList<>();
		for (Context context : contexts.values()) {
			result.add(context.metric);
		}
		return ImmutableList.copyOf(result);
	}
	
	/**
	 * Registers the specified metric. If the ID was previously used by another
	 * metric, that metric gets unregistered. Call this method will null for the
	 * metric parameter to unregister a metric without registering another one.
	 * 
	 * @param id the id
	 * @param metric the metric or null
	 */
	public synchronized void register(String id, Metric<?> metric) {
		unregister(id);
		if (metric != null) {
			Context context = new Context(metric);
			contexts.put(id, context);
			metric.onRegister(context);
		}
	}

	/**
	 * Unregisters the metric with the specified ID. Does nothing if no metric
	 * with that ID is currently registered. This method does the same as
	 * register(id, null).
	 * 
	 * @param id the id
	 */
	public synchronized void unregister(String id) {
		Context context = contexts.remove(id);
		if (context != null) {
			context.metric.onUnregister(context);
		}
	}

	/**
	 * The {@link MetricContext} implementation.
	 */
	class Context implements MetricContext {
		
		private final Metric<?> metric;

		Context(Metric<?> metric) {
			this.metric = metric;
		}
		
	}
	
}
