/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.dependency_injection;

import java.util.HashMap;
import java.util.Map;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import name.martingeisse.serverblob.console.wicket.MyWicketApplication;
import name.martingeisse.serverblob.http.VirtualHostFilter;

/**
 *
 */
public class WebModule extends ServletModule {

	// override
	@Override
	protected void configureServlets() {
		bind(WebApplication.class).to(MyWicketApplication.class);
		// bind filters
		{
			bind(VirtualHostFilter.class).in(Singleton.class);
			filterRegex("/.*").through(VirtualHostFilter.class);
		}
		// bind Wicket
		{
			bind(MyWicketFilter.class).in(Singleton.class);
			final Map<String, String> initParams = new HashMap<String, String>(1);
			initParams.put(WicketFilter.FILTER_MAPPING_PARAM, "/*");
			filterRegex("/.*").through(MyWicketFilter.class, initParams);
		}
	}

}
