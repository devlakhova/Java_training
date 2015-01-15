package com.mycompany.app.di.structure.bo;

import com.mycompany.app.di.Bean;
import com.mycompany.app.di.structure.dao.UserDao;

/**
 * Created by devlakhova on 1/15/15.
 */

@Bean
public class UserBo {

    private UserDao userDao;

    private RecommendationBo recommendationBo;

    public String getUser(int i) {
        return userDao.getUser(i);
    }
}
