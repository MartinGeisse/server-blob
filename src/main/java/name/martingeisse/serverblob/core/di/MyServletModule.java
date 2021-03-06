/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.core.di;

import java.util.HashMap;
import java.util.Map;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import name.martingeisse.serverblob.console.wicket.MyWicketApplication;
import name.martingeisse.serverblob.core.gui.wicket.MyWicketFilter;
import name.martingeisse.serverblob.http.VirtualHostFilter;

/**
 * This module provides the low-level servlet objects.
 */
public class MyServletModule extends ServletModule {

	// override
	@Override
	protected void configureServlets() {

		//
		// TODO clean up
		//
		
		// bind filters
		{
			bind(VirtualHostFilter.class).in(Singleton.class);
			filterRegex("/.*").through(VirtualHostFilter.class);
		}
		
		// bind Wicket
		{
			bind(WebApplication.class).to(MyWicketApplication.class);
			bind(MyWicketFilter.class).in(Singleton.class);
			final Map<String, String> initParams = new HashMap<String, String>(1);
			initParams.put(WicketFilter.FILTER_MAPPING_PARAM, "/*");
			filterRegex("/.*").through(MyWicketFilter.class, initParams);
		}
		
	}

}
