package com.mycompany.app.di;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by devlakhova on 1/15/15.
 */
public class PropertiesHolder {
    private static Map<String, String> propertiesMap = new HashMap<String, String>();
    public static String getProperty(String propertyName) {
        return propertiesMap.get(propertyName);
    }

    public static void setProperty(String propertyName, String propertyValue) {
        propertiesMap.put(propertyName, propertyValue);
    }
}
