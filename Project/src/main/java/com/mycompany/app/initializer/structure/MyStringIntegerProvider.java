package com.mycompany.app.initializer.structure;

/**
 * Created by devlakhova on 1/15/15.
 */
public class MyStringIntegerProvider implements ValueProvider {
    @Override
    public Object provide() {
        return String.valueOf((int)(Math.random() * Integer.MAX_VALUE));
    }
}
