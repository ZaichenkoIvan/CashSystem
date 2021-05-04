package com.shop.model.service.impl;

import com.shop.model.domain.Check;
import com.shop.model.domain.Goods;
import com.shop.model.domain.Order;
import com.shop.model.domain.User;
import com.shop.model.entity.CheckEntity;
import com.shop.model.entity.OrderEntity;
import com.shop.model.exception.*;
import com.shop.model.repositories.CheckRepository;
import com.shop.model.repositories.OrderRepository;
import com.shop.model.service.CheckService;
import com.shop.model.service.GoodService;
import com.shop.model.service.mapper.CheckMapper;
import com.shop.model.service.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CheckServiceImpl implements CheckService {

    private static final int CANCELED = 1;
    private static final int NOT_CANCELLED = 0;
    private final CheckRepository checkRepository;
    private final OrderRepository orderRepository;
    private final CheckMapper checkMapper;
    private final OrderMapper orderMapper;
    private final GoodService goodsService;

    @Override
    public Order addOrder(Order order) {
        if (Objects.isNull(order) || Objects.isNull(order.getNds())
                || order.getQuant() < 0 || order.getQuant() > 100000
                || order.getNds() < 0 || order.getNds() > 100) {
            log.warn("Data for add order is uncorrected");
            throw new InvalidDataRuntimeException("Data for add order is uncorrected");
        }

        Goods goods = order.getGoods().getName().equals("")
                ? goodsService.findByCode(order.getGoods().getCode())
                : goodsService.findByName(order.getGoods().getName());

        if (Objects.isNull(goods)) {
            log.warn("Good is not exist");
            throw new EntityNotFoundRuntimeException("Good is not exist");
        }

        order.setGoods(goods);
        order.setPrice(goods.getPrice());
        order.setTotal(BigDecimal.valueOf(order.getQuant()).multiply(BigDecimal.valueOf(order.getPrice())).doubleValue());
        order.setNdstotal(BigDecimal.valueOf(order.getTotal()).multiply(BigDecimal.valueOf(order.getNds())).divide(new BigDecimal(100)).doubleValue());
        order.setCanceled(NOT_CANCELLED);

        return order;
    }

    @Override
    public void addCheck(User user, List<Order> orders) {
        if (user == null) {
            log.warn("User isn't exist");
            throw new InvalidDataRuntimeException("User isn't exist");
        }

        Check check = new Check();
        check.setCreator(user);
        double total = orders.stream().mapToDouble(Order::getTotal).sum();
        check.setTotal(total);
        check.setCanceled(NOT_CANCELLED);

        CheckEntity checkEntity = checkRepository.save(checkMapper.checkToCheckEntity(check));
        check = checkMapper.checkEntityToCheck(checkEntity);

        for (Order order : orders) {
            order.setCheck(check);
            OrderEntity orderEntity = orderMapper.orderToOrderEntity(order);
            orderRepository.save(orderEntity);
        }
    }

    @Override
    public Check findById(Long checkId) {
        if (Objects.isNull(checkId) || checkId < 0) {
            log.warn("Id not exist");
            throw new InvalidIdRuntimeException("Id not exist");
        }

        return checkMapper.checkEntityToCheck(checkRepository.findById(checkId)
                .orElse(null));
    }

    @Override
    public List<Order> findOrderByCheck(Long checkId) {
        if (Objects.isNull(checkId) || checkId < 0 ) {
            log.warn("Id not exist");
            throw new InvalidIdRuntimeException("Id not exist");
        }

        Check check = checkMapper.checkEntityToCheck(checkRepository.findById(checkId)
                .orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find check by this id")));

        CheckEntity checkEntity = checkMapper.checkToCheckEntity(check);
        List<OrderEntity> orderEntities = orderRepository.findAllByCheck(checkEntity);

        return orderEntities.isEmpty() ?
                Collections.emptyList() :
                orderEntities.stream()
                        .map(orderMapper::orderEntityToOrder)
                        .collect(Collectors.toList());
    }

    @Override
    public void cancelOrder(List<Order> orders, Integer count) {
        if (orders.isEmpty() || Objects.isNull(count) || count < 0) {
            log.warn("Order not exist");
            throw new OrderNotExistRuntimeException("Order not exist");
        }

        Order order = orders.get(count - 1);
        order.setCanceled(CANCELED);

        OrderEntity orderEntity = orderMapper.orderToOrderEntity(order);
        orderRepository.save(orderEntity);

        double total = orders.stream()
                .filter(spec -> spec.getCanceled() == 0)
                .mapToDouble(Order::getTotal).sum();
        Check check = order.getCheck();
        check.setTotal(total);

        CheckEntity checkEntity = checkMapper.checkToCheckEntity(check);
        checkRepository.save(checkEntity);
    }

    @Override
    public void cancelCheck(Check check) {
        if (Objects.isNull(check)) {
            log.warn("Check not exist");
            throw new CheckNotExistRuntimeException("Check not exist");
        }

        check.setCanceled(CANCELED);

        CheckEntity checkEntity = checkMapper.checkToCheckEntity(check);
        checkRepository.save(checkEntity);
    }
}
