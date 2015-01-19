package com.mycompany.app.di.structure.Injectors;

import com.mycompany.app.di.structure.InitializationUtils;
import com.mycompany.app.di.structure.bo.*;
import com.mycompany.app.di.structure.service.RecommendationService;
import com.mycompany.app.di.structure.service.RecommendationServiceImpl;

/**
 * Created by devlakhova on 1/19/15.
 */
public class RecomendationServiceInjectorImpl implements RecomendationServiceInjector {
    @Override
    public RecommendationService getService() {
        UserBo userBo = null;
        RecommendationBo recommendationBo = null;
        if (!InitializationUtils.isInitializing(UserBo.class)) {
            userBo = new UserBoInjectorImpl().getUserBo();
        }
        if (!InitializationUtils.isInitializing(RecommendationBo.class)) {
            recommendationBo = new RecommendationBoInjectorImpl().getRecommendationBo();
        }
        return new RecommendationServiceImpl(userBo, recommendationBo);
    }
}
