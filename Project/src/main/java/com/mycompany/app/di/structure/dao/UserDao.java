package com.mycompany.app.di.structure.dao;

import com.mycompany.app.di.Bean;

/**
 * Created by devlakhova on 1/15/15.
 */

@Bean
public interface UserDao {
    String getUser(int i);
}
