package com.mycompany.app.initializer.structure;

/**
 * Created by devlakhova on 1/14/15.
 */
public class HProvider implements ValueProvider {
    @Override
    public Object provide() {
        H value = new H();
        value.setS("This is string from provider");
        return value;
    }
}
