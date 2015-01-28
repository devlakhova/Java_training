package com.mycompany.app.di;

import com.mycompany.app.di.structure.dao.UserDao;
import com.mycompany.app.di.structure.dao.UserDaoImpl;
import com.mycompany.app.di.structure.service.RecommendationService;
import org.reflections.Reflections;

import java.lang.reflect.*;
import java.util.*;

/**
 * Created by devlakhova on 1/15/15.
 */
public class Runner {

    private static Map<Class, Class> diMap = new HashMap<Class, Class>();
    private static Set <Class<?>> initializeStack = new HashSet<Class<?>>();
    private static Map<Class, List<Object>> applicationScope = new HashMap<Class, List<Object>>();

    public static void main(String[] args) {

        ClassLoader classLoader = Runner.class.getClassLoader();

        Runner runner = new Runner();


        Reflections reflections = new Reflections("com.mycompany.app");
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(Bean.class);
        for (Class<?> implementationClass : types) {
            for (Class iface : implementationClass.getInterfaces()) {
                diMap.put(iface, implementationClass);
            }
        }

        PropertiesHolder.setProperty(UserDaoImpl.IS_SHOW_ALL_USERS, "false");

        RecommendationService recommendationService = runner.getBean(RecommendationService.class);
        System.out.println(recommendationService.getRecommendationForUser(1));


        PropertiesHolder.setProperty(UserDaoImpl.IS_SHOW_ALL_USERS, "true");
        reloadBean(UserDao.class);
        System.out.println(recommendationService.getRecommendationForUser(1));
    }

    private static   void reloadBean(Class<?> userDaoClass) {
        List <?> beansToReload = applicationScope.get(userDaoClass);
        for (Object proxy : beansToReload) {
            RecommendationServiceProxy beanToReload = (RecommendationServiceProxy) proxy;
            beanToReload.reload();
        }
    }

    private <T> T getBean(Class<T> beanClassInterface) {
        Class implementationClass = diMap.get(beanClassInterface);
        T bean = null;

        if (isInitialized(beanClassInterface)) {
            return null;
        }

        initializeStack.add(beanClassInterface);
        Constructor <T> constr = null;
        try {
            constr = implementationClass.getConstructor();
            bean = constr.newInstance();


            for (Field field : implementationClass.getDeclaredFields()) {
                if (field.getType().isAnnotationPresent(Bean.class)) {
                    T fieldValue = getBean((Class<T>) field.getGenericType());
                    field.setAccessible(true);
                    field.set(bean, fieldValue);
                }
            }
            initializeStack.remove(beanClassInterface);
            if (beanClassInterface.isInterface()) {
                RecommendationServiceProxy obj = new RecommendationServiceProxy(bean);
                T proxyObject = (T) Proxy.newProxyInstance(beanClassInterface.getClassLoader(), new Class[]{beanClassInterface}, obj);
                putBeanToScope(beanClassInterface, obj);
                return proxyObject;
            }
            return bean;

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static boolean isInitialized(Class<?> clazz) {
        return initializeStack.contains(clazz);
    }

    public static void putBeanToScope(Class classa, Object value) {
        List <Object> beanList = applicationScope.get(classa);
        if (beanList == null) {
            beanList = new ArrayList<Object>();
        }
        beanList.add(value);
        applicationScope.put(classa, beanList);
    }
}
