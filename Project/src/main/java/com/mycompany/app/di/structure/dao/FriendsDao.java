package com.mycompany.app.di.structure.dao;

import com.mycompany.app.di.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by devlakhova on 1/15/15.
 */
@Bean
public class FriendsDao {

    public List<String> getFriends(String user) {
        List<String> ret = new ArrayList<String>(5);
        if ("AllUsers".equals(user)) {
            ret.addAll(Arrays.asList("1,2,3,4,5".split(",")));
        } else {
            ret.add("6");
        }
        return ret;
    }
}
