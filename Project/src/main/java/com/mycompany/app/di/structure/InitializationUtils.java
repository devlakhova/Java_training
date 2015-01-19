package com.mycompany.app.di.structure;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by devlakhova on 1/19/15.
 */
public class InitializationUtils {

    private static Set<Class<?>> initializeStack = new HashSet<Class<?>>();

    public static boolean isInitializing(Class<?> clazz) {
        return initializeStack.contains(clazz);
    }
}
