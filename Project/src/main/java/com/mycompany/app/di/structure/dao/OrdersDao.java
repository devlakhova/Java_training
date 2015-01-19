package com.mycompany.app.di.structure.dao;

import com.mycompany.app.di.Bean;

import java.util.List;

/**
 * Created by devlakhova on 1/19/15.
 */

@Bean
public interface OrdersDao {
    List<String> getOrders(String user);
}
