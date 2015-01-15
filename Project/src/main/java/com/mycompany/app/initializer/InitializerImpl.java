package com.mycompany.app.initializer;

import com.mycompany.app.initializer.structure.A;
import com.mycompany.app.initializer.structure.Providere;
import com.mycompany.app.initializer.structure.ValueProvider;
import sun.jvm.hotspot.jdi.InterfaceTypeImpl;
import sun.jvm.hotspot.oops.FieldType;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import sun.reflect.generics.repository.ClassRepository;
import sun.rmi.rmic.iiop.InterfaceType;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.security.Provider;
import java.util.*;

/**
 * Created by devlakhova on 1/12/15.
 */
public class InitializerImpl {

    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();
    private static final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS = getWrapperMap();

    private static Set <Class<?>> initializeStack = new HashSet<Class<?>>();

    public <T> T getInstance(Class<T> aClass) {
        T instance;
        try {
            if (aClass.isPrimitive()) {
                aClass = wrap(aClass);
            }
            if (isWrapperType(aClass)) {
                return (T) getPrimitive(aClass);
            } else if (aClass.equals(String.class)) {
                return (T) new String("abc");
            } else if (aClass.equals(Date.class)) {
                return (T) new Date();
            } else if (aClass.isEnum()) {
                return aClass.getEnumConstants()[0];
            } else if (aClass.isArray()) {
                Object arr = Array.newInstance(aClass.getComponentType(), 1);
                Array.set(arr, 0, getInstance(aClass.getComponentType()));
                return (T) arr;
            }

            if (isInitialized(aClass))
                return null;

            Constructor <T> constr = aClass.getConstructor();
            instance = constr.newInstance();
            initializeStack.add(aClass);


            for (PropertyDescriptor propertyDescriptor : java.beans.Introspector.getBeanInfo(aClass).getPropertyDescriptors()) {
                if (propertyDescriptor.getPropertyType().equals(Class.class)) {
                    continue;
                }
                Field field = aClass.getDeclaredField(propertyDescriptor.getName());
                Method setter = propertyDescriptor.getWriteMethod();
                if (field.isAnnotationPresent(Providere.class)) {
                    Providere annotation = field.getAnnotation(Providere.class);
                    Class<? extends ValueProvider> annotadedClass = annotation.value();
                    Object obj = annotadedClass.newInstance().provide();
                    setter.invoke(instance,  obj);
                } else {
                    setter.invoke(instance, getInstance(setter.getGenericParameterTypes()[0]));
                }
            }

            System.out.println(instance);
            return instance;
        }
        catch (InstantiationException ex) {
            System.out.println("Couldn't instantiate");
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            System.out.println("No Access");
            ex.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Constructor constr = (Constructor) aClass.getConstructors()[0];
            ArrayList <Object> parameters = new ArrayList<Object>();
            for (Class zClass : constr.getParameterTypes()) {
                parameters.add(getInstance(zClass));
            }
            try {
                if(parameters.size() > 0)
                    instance = (T) constr.newInstance(parameters);
                else
                    instance = aClass.newInstance();
                return instance;
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        } catch (IntrospectionException e) {

        } catch (NoSuchFieldException e) {

        } finally {
        }
        return null;
    }

    public Object getInstance(Type type) throws IllegalAccessException, InstantiationException {
        if (type instanceof Class) {
            return getInstance((Class)type);
        }
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
        Collection <T> collection;
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
            map = (Map<T,E>)mapType.newInstance();
        }
        map.put(key, value);
        return map;
    }

    public Object getPrimitive(Class<?> aClass) {
        if (aClass.isPrimitive()) {
            aClass = wrap(aClass);
        }
        if (aClass.equals(Boolean.class)) {
            return new Random().nextBoolean();
        } else if (aClass.equals(Byte.class)) {
            return (byte) (new Random().nextInt(Byte.MAX_VALUE * 2 + 1) - Byte.MAX_VALUE);
        } else if (aClass.equals(Short.class)) {
            return (short) (new Random().nextInt(Short.MAX_VALUE * 2 + 1) - Short.MAX_VALUE);
        } else if (aClass.equals(Integer.class)) {
            return new Random().nextInt();
        } else if (aClass.equals(Long.class)) {
            return new Random().nextLong();
        } else if (aClass.equals(Float.class)) {
            return new Random().nextFloat();
        } else if (aClass.equals(Double.class)) {
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

    private static <T> Class<T> wrap(Class<T> c) {
        return c.isPrimitive() ? (Class<T>) PRIMITIVES_TO_WRAPPERS.get(c) : c;
    }

    private static Map<Class<?>,Class<?>> getWrapperMap() {
        Map <Class<?>, Class<?>> wrap = new HashMap<Class<?>, Class<?>>();
        wrap.put(boolean.class, Boolean.class);
        wrap.put(byte.class, Byte.class);
        wrap.put(char.class, Character.class);
        wrap.put(double.class, Double.class);
        wrap.put(float.class, Float.class);
        wrap.put(int.class, Integer.class);
        wrap.put(long.class, Long.class);
        wrap.put(short.class, Short.class);
        wrap.put(void.class, Void.class);
        return wrap;
    }

}
