package com.mycompany.app.di.structure.service;

import com.mycompany.app.di.Bean;
import com.mycompany.app.di.structure.bo.RecommendationBo;
import com.mycompany.app.di.structure.bo.UserBo;

/**
 * Created by devlakhova on 1/15/15.
 */

@Bean
public class RecommendationService {

    private UserBo userBo;

    private RecommendationBo recommendationBo;


    public String getRecommendationForUser(int i) {
        return recommendationBo.getRecommendation(userBo.getUser(i));
    }
}
