package com.mycompany.app.di;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by devlakhova on 1/19/15.
 */
public class RecommendationServiceProxy implements InvocationHandler {

    private Object object;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(this.object, args);
    }

    public RecommendationServiceProxy(Object object) {
        this.object = object;
    }

    public void reload() {
        try {
            Class aClass = this.object.getClass();
            object = aClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
