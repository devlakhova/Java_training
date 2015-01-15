package com.mycompany.app.di;

import com.mycompany.app.di.structure.dao.UserDaoImpl;
import com.mycompany.app.di.structure.service.RecommendationService;

/**
 * Created by devlakhova on 1/15/15.
 */
public class Runner {

    public static void main(String[] args) {
        Runner runner = new Runner();


        PropertiesHolder.setProperty(UserDaoImpl.IS_SHOW_ALL_USERS, "false");

        RecommendationService recommendationService = runner.getBean(RecommendationService.class);
        System.out.println(recommendationService.getRecommendationForUser(1));


        PropertiesHolder.setProperty(UserDaoImpl.IS_SHOW_ALL_USERS, "true");
        reloadBean(UserDaoImpl.class);
        System.out.println(recommendationService.getRecommendationForUser(1));
    }

    private static void reloadBean(Class<?> userDaoClass) {

    }

    private <T> T getBean(Class<T> recommendationServiceClass) {
        return null;//TODO
    }
}
