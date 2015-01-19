package com.mycompany.app.di.structure.bo;

import com.mycompany.app.di.Bean;
import com.mycompany.app.di.structure.dao.FriendsDao;
import com.mycompany.app.di.structure.dao.FriendsDaoImpl;
import com.mycompany.app.di.structure.dao.OrdersDao;
import com.mycompany.app.di.structure.dao.OrdersDaoImpl;

import java.util.List;

/**
 * Created by devlakhova on 1/15/15.
 */

@Bean
public class RecommendationBoImpl implements RecommendationBo {

    private FriendsDao friendsDao;

    private UserBo userBo;

    private OrdersDao ordersDao;

    public RecommendationBoImpl () {}

    public RecommendationBoImpl(FriendsDao friendsDao, UserBo userBo, OrdersDao ordersDao) {
        this.friendsDao = friendsDao;
        this.userBo = userBo;
        this.ordersDao = ordersDao;
    }

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
