/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.http;

import java.io.IOException;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.inject.Inject;

/**
 * Intercepts HTTP requests to virtual hosts.
 */
public class VirtualHostFilter implements Filter {

	private final Set<VirtualHostProvider> virtualHostProviders;

	/**
	 * Constructor.
	 * @param virtualHostProviders the virtual host providers
	 */
	@Inject
	public VirtualHostFilter(Set<VirtualHostProvider> virtualHostProviders) {
		this.virtualHostProviders = virtualHostProviders;
	}

	// override
	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
	}

	// override
	@Override
	public void destroy() {
	}

	// override
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
			final HttpServletRequest httpServletRequest = (HttpServletRequest)request;
			String hostName = httpServletRequest.getHeader("host");
			if (hostName != null) {
				{
					int index = hostName.indexOf('.');
					if (index >= 0) {
						hostName = hostName.substring(0, index);
					}
				}
				for (final VirtualHostProvider virtualHostProvider : virtualHostProviders) {
					final VirtualHostInfo virtualHostInfo = virtualHostProvider.getVirtualHost(hostName);
					if (virtualHostInfo != null) {
						final HttpServletResponse httpServletResponse = (HttpServletResponse)response;
						virtualHostInfo.getRequestHandler().handle(httpServletRequest, httpServletResponse);
						return;
					}
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
