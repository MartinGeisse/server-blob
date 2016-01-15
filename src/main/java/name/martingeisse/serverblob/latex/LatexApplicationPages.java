/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.latex;

import com.google.common.collect.ImmutableList;
import name.martingeisse.serverblob.app_pages.ApplicationPages;
import name.martingeisse.serverblob.app_pages.Mounter;
import name.martingeisse.serverblob.app_pages.RootPageReference;
import name.martingeisse.serverblob.dependency_injection.Extension;

/**
 *
 */
@Extension
public class LatexApplicationPages implements ApplicationPages {

	// override
	@Override
	public void mount(final Mounter mounter) {
		mounter.mount("/", LatexTestPage.class);
	}

	// override
	@Override
	public String getName() {
		return "LaTeX";
	}
	
	// override
	@Override
	public ImmutableList<RootPageReference> getRootPageReferences() {
		return ImmutableList.of(new RootPageReference("Test Page", LatexTestPage.class));
	}

}
