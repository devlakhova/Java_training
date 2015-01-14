package com.mycompany.app.initializer;

import com.mycompany.app.initializer.structure.A;
import sun.jvm.hotspot.jdi.InterfaceTypeImpl;
import sun.jvm.hotspot.oops.FieldType;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import sun.reflect.generics.repository.ClassRepository;
import sun.rmi.rmic.iiop.InterfaceType;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * Created by devlakhova on 1/12/15.
 */
public class InitializerImpl {

    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();
    private static Set <Class<?>> initializeStack = new HashSet<Class<?>>();

    public <T> T getInstance(Class<T> aClass) {
        T instance;
        try {
            if (isWrapperType(aClass) || aClass.isPrimitive()) {
                return (T) getPrimitive(aClass);
            } else if (aClass.equals(String.class)) {
                return (T) new String("abc");
            } else if (aClass.equals(Date.class)) {
                return (T) new Date();
            } else if (aClass.isEnum()) {
                return aClass.getEnumConstants()[0];
            }

            if (isInitialized(aClass))
                return null;

            Constructor <T> constr = aClass.getConstructor();
            instance = constr.newInstance();
            initializeStack.add(aClass);

            for (Method method : findSetters(aClass)) {
                method.invoke(instance, getInstance(method.getGenericParameterTypes()[0]));
            }

            System.out.println(instance);
            return instance;
        }
        catch (InstantiationException ex) {
            System.out.println("Couldn't instantiate");
            ex.printStackTrace();
        }
        catch (IllegalAccessException ex) {
            System.out.println("No Access");
            ex.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Constructor <T> constr = (Constructor<T>) aClass.getConstructors()[0];
            ArrayList <Object> parameters = new ArrayList<Object>();
            for (Class zClass : constr.getParameterTypes()) {
                parameters.add(getInstance(zClass));
            }
            try {
                if(parameters.size() > 0)
                    instance = (T) constr.newInstance(parameters);
                else
                    instance = aClass.newInstance();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }
        finally {
        }
        return null;
    }

    public Object getInstance(Type type) throws IllegalAccessException, InstantiationException {
        if (type instanceof Class) {
            return getInstance((Class)type);
        }
        //TODO

        Class rawType = ((ParameterizedTypeImpl)type).getRawType();

        if (Collection.class.isAssignableFrom(rawType)) {
            Class elementType = (Class) ((ParameterizedTypeImpl)type).getActualTypeArguments()[0];
            return getCollection(rawType, elementType);
        } else if (Map.class.isAssignableFrom(rawType)) {
            Class keyType = (Class) ((ParameterizedTypeImpl)type).getActualTypeArguments()[0];
            Class valueType = (Class) ((ParameterizedTypeImpl)type).getActualTypeArguments()[1];
            return getMap(rawType, keyType, valueType);
        }
        return null;
    }

    public <T> Collection<T> getCollection(Class collectionType, Class<T> generic) throws IllegalAccessException, InstantiationException {
        T element = getInstance(generic);

        Collection <T> collection = null;
        if (collectionType.isInterface()) {
            if (List.class.isAssignableFrom(collectionType)) {
                collection = new ArrayList<T>();
            } else {
                collection = new HashSet<T>();
            }
        } else {
            collection = (Collection)collectionType.newInstance();
        }
        collection.add(element);
        return collection;
    }

    public <T,E> Map<T,E> getMap(Class mapType, Class<T> keyType, Class<E> valueType) throws IllegalAccessException, InstantiationException {
        Map <T,E> map = null;
        T key = getInstance(keyType);
        E value = getInstance(valueType);
        if (mapType.isInterface()) {
            if (SortedMap.class.isAssignableFrom(mapType)) {
                map = new TreeMap<T,E>();
            }
            else {
                map = new HashMap<T,E>();
            }

        } else {
            map = (Map)mapType.newInstance();
        }
        map.put(key, value);
        return map;
    }

    public Object getPrimitive(Class<?> aClass) {
        if (aClass.equals(boolean.class) || aClass.equals(Boolean.class)) {
            return new Random().nextBoolean();
        } else if (aClass.equals(byte.class) || aClass.equals(Byte.class)) {
            return (byte) (new Random().nextInt(Byte.MAX_VALUE * 2 + 1) - Byte.MAX_VALUE);
        } else if (aClass.equals(short.class) || aClass.equals(Short.class)) {
            return (short) (new Random().nextInt(Short.MAX_VALUE * 2 + 1) - Short.MAX_VALUE);
        } else if (aClass.equals(int.class) || aClass.equals(Integer.class)) {
            return new Random().nextInt();
        } else if (aClass.equals(long.class) || aClass.equals(Long.class)) {
            return new Random().nextLong();
        } else if (aClass.equals(float.class) || aClass.equals(Float.class)) {
            return new Random().nextFloat();
        } else if (aClass.equals(double.class) || aClass.equals(Double.class)) {
            return new Random().nextDouble();
        } else {
            throw new IllegalArgumentException(
                    "Class type " + aClass + " not supported");
        }
    }

    public static boolean isInitialized(Class<?> clazz) {
        return initializeStack.contains(clazz);
    }

    public static boolean isWrapperType(Class<?> clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }

    private static Set<Class<?>> getWrapperTypes()
    {
        Set<Class<?>> ret = new HashSet<Class<?>>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        return ret;
    }
    static ArrayList<Method> findSetters(Class<?> c) {
        ArrayList<Method> list = new ArrayList<Method>();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods)
            if (isSetter(method))
                list.add(method);
        return list;
    }

    public static boolean isSetter(Method method) {
        return Modifier.isPublic(method.getModifiers()) &&
                method.getReturnType().equals(void.class) &&
                method.getParameterTypes().length == 1 &&
                method.getName().matches("^set[A-Z].*");
//        java.beans.Introspector.getBeanInfo(A.class).getPropertyDescriptors()[0].
//                java.beans.Introspector.getBeanInfo(A.class).
    }

    public static boolean isGetter(Method method) {
        if (Modifier.isPublic(method.getModifiers()) &&
                method.getParameterTypes().length == 0) {
            if (method.getName().matches("^get[A-Z].*") &&
                    !method.getReturnType().equals(void.class))
                return true;
            if (method.getName().matches("^is[A-Z].*") &&
                    method.getReturnType().equals(boolean.class))
                return true;
        }
        return false;
    }
}
