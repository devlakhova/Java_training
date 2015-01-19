package com.mycompany.app.di.structure.bo;

import com.mycompany.app.di.Bean;
import com.mycompany.app.di.structure.dao.UserDao;

/**
 * Created by devlakhova on 1/15/15.
 */

@Bean
public class UserBoImpl implements UserBo {

    private UserDao userDao;

    private RecommendationBo recommendationBo;

    @Override
    public String getUser(int i) {
        return userDao.getUser(i);
    }

    public UserBoImpl() {}


    public UserBoImpl(UserDao userDao, RecommendationBo recommendationBo) {
        this.userDao = userDao;
        this.recommendationBo = recommendationBo;
    }
}
