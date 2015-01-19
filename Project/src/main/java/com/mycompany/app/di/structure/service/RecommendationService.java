package com.mycompany.app.di.structure.service;

import com.mycompany.app.di.Bean;

/**
 * Created by devlakhova on 1/16/15.
 */

@Bean
public interface RecommendationService {
    String getRecommendationForUser(int i);
}
