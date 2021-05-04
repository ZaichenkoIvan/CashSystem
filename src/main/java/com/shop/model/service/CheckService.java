package com.shop.model.service;

import com.shop.model.domain.Check;
import com.shop.model.domain.Order;
import com.shop.model.domain.User;

import java.util.List;

public interface CheckService {

    Order addOrder(Order order);

    void addCheck(User user, List<Order> orders);

    Check findById(Long checkId);

    List<Order> findOrderByCheck(Long checkId);

    void cancelOrder(List<Order> orders, Integer count);

    void cancelCheck(Check check);
}
