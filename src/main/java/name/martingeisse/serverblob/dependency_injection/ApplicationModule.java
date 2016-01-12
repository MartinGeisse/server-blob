/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.dependency_injection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

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
