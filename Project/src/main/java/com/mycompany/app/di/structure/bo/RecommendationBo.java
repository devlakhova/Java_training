package com.mycompany.app.di.structure.bo;

import com.mycompany.app.di.Bean;

/**
 * Created by devlakhova on 1/19/15.
 */

@Bean
public interface RecommendationBo {
    public String getRecommendation(String user);
}
