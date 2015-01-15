package com.mycompany.app.di.structure.bo;

import com.mycompany.app.di.Bean;
import com.mycompany.app.di.structure.dao.FriendsDao;
import com.mycompany.app.di.structure.dao.OrdersDao;

import java.util.List;

/**
 * Created by devlakhova on 1/15/15.
 */

@Bean
public class RecommendationBo {

    private FriendsDao friendsDao;

    private UserBo userBo;

    private OrdersDao ordersDao;

    public String getRecommendation(String user) {
        List<String> friends = friendsDao.getFriends(user);
        List<String> orders = ordersDao.getOrders(user);
        StringBuilder sb = new StringBuilder();
        for (String friend : friends) {
            sb.append("For user: ").append(friend).append(" we recommend:");

            for (String order : orders) {
                sb.append(order).append(",");
            }
        }
        return sb.toString();

    }
}
