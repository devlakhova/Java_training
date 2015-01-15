package com.mycompany.app.di.structure.dao;

import com.mycompany.app.di.Bean;
import com.mycompany.app.di.PropertiesHolder;

/**
 * Created by devlakhova on 1/15/15.
 */

@Bean
public class UserDaoImpl implements UserDao {

    public static final String IS_SHOW_ALL_USERS = "isShowAllUsers";
    private boolean showAllUsers;

    public boolean isShowAllUsers() {
        return showAllUsers;
    }

    public UserDaoImpl() {
        showAllUsers = Boolean.valueOf(PropertiesHolder.getProperty(IS_SHOW_ALL_USERS));
    }

    @Override
    public String getUser(int i) {
        if (showAllUsers) {
            return "AllUsers";
        } else {
            return "CurrentUser";
        }
    }
}
