package com.shop.model.service.mapper;

import com.shop.model.domain.Check;
import com.shop.model.domain.Goods;
import com.shop.model.domain.Order;
import com.shop.model.entity.CheckEntity;
import com.shop.model.entity.GoodsEntity;
import com.shop.model.entity.OrderEntity;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {OrderMapper.class})
public class OrderMapperTest {
    private static final CheckEntity CHECK_ENTITY = getCheckEntity();

    private static final Check CHECK_DOMAIN = getCheck();

    private static final GoodsEntity GOODS_ENTITY = getGoodEntity();

    private static final Goods GOODS_DOMAIN = getGood();

    private static final OrderEntity ORDER_ENTITY = getOrderEntity();

    private static final Order ORDER_DOMAIN = getOrder();

    @MockBean
    private CheckMapper checkMapper;

    @MockBean
    private GoodMapper goodMapper;

    @Autowired
    private OrderMapper orderMapper;

    @After
    public void resetMock() {
        reset(checkMapper, goodMapper);
    }

    @Test
    public void shouldMapOrderEntityToOrder() {
        when(checkMapper.checkEntityToCheck(any(CheckEntity.class))).thenReturn(CHECK_DOMAIN);
        when(goodMapper.goodEntityToGood(any(GoodsEntity.class))).thenReturn(GOODS_DOMAIN);

        Order actual = orderMapper.orderEntityToOrder(ORDER_ENTITY);

        assertThat(actual.getId(), is(ORDER_DOMAIN.getId()));
        assertThat(actual.getQuant(), is(ORDER_DOMAIN.getQuant()));
        assertThat(actual.getPrice(), is(ORDER_DOMAIN.getPrice()));
        assertThat(actual.getTotal(), is(ORDER_DOMAIN.getTotal()));
        assertThat(actual.getNdstotal(), is(ORDER_DOMAIN.getNdstotal()));
        assertThat(actual.getNds(), is(ORDER_DOMAIN.getNds()));
        assertThat(actual.getCanceled(), is(ORDER_DOMAIN.getCanceled()));
        assertThat(actual.getCheck(), is(ORDER_DOMAIN.getCheck()));
        assertThat(actual.getGoods(), is(ORDER_DOMAIN.getGoods()));
    }

    @Test
    public void shouldMapOrderToOrderEntity() {
        when(checkMapper.checkToCheckEntity(any(Check.class))).thenReturn(CHECK_ENTITY);
        when(goodMapper.goodToGoodEntity(any(Goods.class))).thenReturn(GOODS_ENTITY);

        OrderEntity actual = orderMapper.orderToOrderEntity(ORDER_DOMAIN);

        assertThat(actual.getId(), is(ORDER_ENTITY.getId()));
        assertThat(actual.getQuant(), is(ORDER_ENTITY.getQuant()));
        assertThat(actual.getPrice(), is(ORDER_ENTITY.getPrice()));
        assertThat(actual.getTotal(), is(ORDER_ENTITY.getTotal()));
        assertThat(actual.getNdstotal(), is(ORDER_ENTITY.getNdstotal()));
        assertThat(actual.getNds(), is(ORDER_ENTITY.getNds()));
        assertThat(actual.getCanceled(), is(ORDER_ENTITY.getCanceled()));
        assertThat(actual.getCheck(), is(ORDER_ENTITY.getCheck()));
        assertThat(actual.getGoods(), is(ORDER_ENTITY.getGoods()));
    }

    @Test
    public void mapGoodToGoodEntityShouldReturnNull() {
        OrderEntity actual = orderMapper.orderToOrderEntity(null);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void mapGoodEntityToGoodShouldReturnNull() {
        Order actual = orderMapper.orderEntityToOrder(null);

        assertThat(actual, is(nullValue()));
    }

    private static GoodsEntity getGoodEntity() {
        return new GoodsEntity(1L, 1, "Name", 100, 100,
                "measure", "comments", null);
    }

    private static Goods getGood() {
        return Goods.builder()
                .id(1L)
                .code(1)
                .name("Name")
                .quant(100)
                .price(100)
                .measure("measure")
                .comments("comments")
                .build();
    }

    private static CheckEntity getCheckEntity() {
        return new CheckEntity(1L, null, 100.0, 0.75,
                1, 1, null, null);
    }

    private static Check getCheck() {
        return Check.builder()
                .id(1L)
                .total(100.0)
                .discount(0.75)
                .canceled(1)
                .registration(1)
                .build();
    }

    private static OrderEntity getOrderEntity() {
        return new OrderEntity(1L, 100.0, 100.0, 100.0, 100, 100.0, 1,
                CHECK_ENTITY, GOODS_ENTITY);
    }

    private static Order getOrder() {
        return Order.builder()
                .id(1L)
                .quant(100.0)
                .price(100.0)
                .total(100.0)
                .ndstotal(100.0)
                .nds(100)
                .canceled(1)
                .check(CHECK_DOMAIN)
                .goods(GOODS_DOMAIN)
                .build();
    }
}
