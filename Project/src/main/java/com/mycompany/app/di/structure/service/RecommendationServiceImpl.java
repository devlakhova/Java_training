package com.mycompany.app.di.structure.service;

import com.mycompany.app.di.Bean;
import com.mycompany.app.di.structure.bo.RecommendationBo;
import com.mycompany.app.di.structure.bo.RecommendationBoImpl;
import com.mycompany.app.di.structure.bo.UserBo;
import com.mycompany.app.di.structure.bo.UserBoImpl;

/**
 * Created by devlakhova on 1/15/15.
 */

@Bean
public class RecommendationServiceImpl implements RecommendationService {

    private UserBo userBo;

    private RecommendationBo recommendationBo;

    public UserBo getUserBo() {
        return userBo;
    }

    public void setUserBo(UserBo userBo) {
        this.userBo = userBo;
    }

    public RecommendationServiceImpl() {}

    public RecommendationServiceImpl(UserBo userBo, RecommendationBo recommendationBo) {
        this.userBo = userBo;
        this.recommendationBo = recommendationBo;
    }



    @Override
    public String getRecommendationForUser(int i) {
        return recommendationBo.getRecommendation(userBo.getUser(i));
    }
}
