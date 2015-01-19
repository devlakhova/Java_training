package com.mycompany.app.di.structure.dao;

import com.mycompany.app.di.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by devlakhova on 1/15/15.
 */

@Bean
public class OrdersDaoImpl implements OrdersDao {

    public OrdersDaoImpl() {

    }

    @Override
    public List<String> getOrders(String user) {
        List<String> ret = new ArrayList<String>(5);
        if ("AllUsers".equals(user)) {
            ret.addAll(Arrays.asList("Prod1,Prod5".split(",")));
        } else {
            ret.add("ProdZZZZ");
        }
        return ret;
    }
}
