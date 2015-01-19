package com.mycompany.app.di.structure.Injectors;

import com.mycompany.app.di.structure.InitializationUtils;
import com.mycompany.app.di.structure.bo.RecommendationBo;
import com.mycompany.app.di.structure.bo.RecommendationBoImpl;
import com.mycompany.app.di.structure.bo.UserBo;
import com.mycompany.app.di.structure.dao.FriendsDao;
import com.mycompany.app.di.structure.dao.OrdersDao;

/**
 * Created by devlakhova on 1/19/15.
 */
public class RecommendationBoInjectorImpl implements RecommendationBoInjector {
    @Override
    public RecommendationBo getRecommendationBo() {

        UserBo userBo = null;
        FriendsDao friendsDao = null;
        OrdersDao ordersDao = null;

        if (!InitializationUtils.isInitializing(UserBo.class)) {
            userBo =  new UserBoInjectorImpl().getUserBo();
        }
        return new RecommendationBoImpl(friendsDao, userBo, ordersDao);
    }
}
