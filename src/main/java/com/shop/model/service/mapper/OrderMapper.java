package com.shop.model.service.mapper;

import com.shop.model.domain.Check;
import com.shop.model.domain.Goods;
import com.shop.model.domain.Order;
import com.shop.model.entity.CheckEntity;
import com.shop.model.entity.GoodsEntity;
import com.shop.model.entity.OrderEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class OrderMapper {
    private CheckMapper checkMapper;
    private GoodMapper goodMapper;

    public Order orderEntityToOrder(OrderEntity orderEntity) {
        if (Objects.isNull(orderEntity)) {
            return null;
        }

        Check check = checkMapper.checkEntityToCheck(orderEntity.getCheck());
        Goods good = goodMapper.goodEntityToGood(orderEntity.getGoods());

        return Order.builder()
                .id(orderEntity.getId())
                .quant(orderEntity.getQuant())
                .price(orderEntity.getPrice())
                .total(orderEntity.getTotal())
                .nds(orderEntity.getNds())
                .ndstotal(orderEntity.getNdstotal())
                .canceled(orderEntity.getCanceled())
                .check(check)
                .goods(good)
                .build();
    }

    public OrderEntity orderToOrderEntity(Order order) {
        if (Objects.isNull(order)) {
            return null;
        }

        CheckEntity check = checkMapper.checkToCheckEntity(order.getCheck());
        GoodsEntity good = goodMapper.goodToGoodEntity(order.getGoods());

        return OrderEntity.builder()
                .id(order.getId())
                .quant(order.getQuant())
                .price(order.getPrice())
                .total(order.getTotal())
                .nds(order.getNds())
                .ndstotal(order.getNdstotal())
                .canceled(order.getCanceled())
                .check(check)
                .goods(good)
                .build();
    }
}
