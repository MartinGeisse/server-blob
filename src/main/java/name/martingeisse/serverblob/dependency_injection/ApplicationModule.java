/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.dependency_injection;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import name.martingeisse.serverblob.configuration.ConfigurationStore;
import name.martingeisse.serverblob.configuration.FileConfigurationStore;

/**
 *
 */
public class ApplicationModule extends AbstractModule {

	// override
	@Override
	protected void configure() {
		
		// obtain reflection data
		Reflections reflections = new Reflections(getClass().getClassLoader());
		Set<Class<?>> extensionPoints = reflections.getTypesAnnotatedWith(ExtensionPoint.class);
		Set<Class<?>> extensions = reflections.getTypesAnnotatedWith(Extension.class);
		
		/*
		 * TODO scanning everything for Es and EPs takes some time. This is one reason why
		 * explicit "plugin" objects that return the extensions and extension points are
		 * an improvement over annotation-based scanning. Are there other arguments pro/con?
		 * 
		 * pro annotations: makes things more modular.
		 * 
		 * con plugin objects: These need to be discovered too.
		 */
		
		// collect extension points and build a map using them as keys
		Map<Class<?>, Multibinder<?>> binderMap = new HashMap<>();
		for (Class<?> extensionPoint : extensionPoints) {
			binderMap.put(extensionPoint, Multibinder.newSetBinder(binder(), extensionPoint));
		}
		
		// bind to extensions
		for (Class<?> extension : extensions) {
			Class<?> extensionPoint = findExtensionPointForExtension(extension);
			Multibinder<?> multibinder = binderMap.get(extensionPoint);
			unsafeBind(multibinder, extension);
		}

		// other bindings
		// TODO for some stupid reason, Eclipse WTP changes the working directory to some weird place, so I have to use an absolute path here...
		// bind(ConfigurationStore.class).toInstance(new FileConfigurationStore(new File("resource/configuration")));
		bind(ConfigurationStore.class).toInstance(new FileConfigurationStore(new File("/Users/martin/workspace/server-blob/resource/configuration")));
		
	}

	private Class<?> findExtensionPointForExtension(Class<?> extensionClass) {
		Class<?> result = null;
		Class<?> currentClass = extensionClass;
		while (currentClass != null) {
			for (Class<?> implementedInterface : currentClass.getInterfaces()) {
				if (implementedInterface.getAnnotation(ExtensionPoint.class) != null) {
					if (result != null) {
						throw new RuntimeException("class " + extensionClass + " implements both extension points " + result.getName() + " and " + implementedInterface.getName());
					}
					result = implementedInterface;
				}
			}
			currentClass = currentClass.getSuperclass();
		}
		if (result == null) {
			throw new RuntimeException("class " + extensionClass + " does not implement any extension point interface");
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private void unsafeBind(Multibinder<?> multibinder, Class<?> extension) {
		Multibinder<Object> dummyTypedMultibinder = (Multibinder<Object>)multibinder;
		Class<Object> dummyTypedExtension = (Class<Object>)extension; 
		dummyTypedMultibinder.addBinding().to(dummyTypedExtension);
	}
	
}
