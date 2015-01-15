package com.mycompany.app.initializer.structure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by devlakhova on 1/15/15.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Providere {

    public Class<? extends ValueProvider> value();
}
