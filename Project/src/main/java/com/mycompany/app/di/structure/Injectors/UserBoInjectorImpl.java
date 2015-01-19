package com.mycompany.app.di.structure.Injectors;

import com.mycompany.app.di.structure.InitializationUtils;
import com.mycompany.app.di.structure.bo.RecommendationBo;
import com.mycompany.app.di.structure.bo.UserBo;
import com.mycompany.app.di.structure.bo.UserBoImpl;
import com.mycompany.app.di.structure.dao.UserDao;

/**
 * Created by devlakhova on 1/19/15.
 */
public class UserBoInjectorImpl implements UserBoInjector {

    @Override
    public UserBo getUserBo() {
        RecommendationBo recommendationBo = null;
        UserDao userDao = null;
        if (!InitializationUtils.isInitializing(RecommendationBo.class)) {
            recommendationBo = new RecommendationBoInjectorImpl().getRecommendationBo();
        }
        return new UserBoImpl(userDao, recommendationBo);
    }
}
