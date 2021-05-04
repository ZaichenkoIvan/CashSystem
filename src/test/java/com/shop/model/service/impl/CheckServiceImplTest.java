package com.shop.model.service.impl;

import com.shop.model.domain.Check;
import com.shop.model.domain.Goods;
import com.shop.model.domain.Order;
import com.shop.model.domain.User;
import com.shop.model.entity.CheckEntity;
import com.shop.model.entity.GoodsEntity;
import com.shop.model.entity.OrderEntity;
import com.shop.model.exception.*;
import com.shop.model.repositories.CheckRepository;
import com.shop.model.repositories.OrderRepository;
import com.shop.model.service.GoodService;
import com.shop.model.service.mapper.CheckMapper;
import com.shop.model.service.mapper.OrderMapper;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CheckServiceImpl.class})
public class CheckServiceImplTest {

    private static final Goods GOOD = getGood();

    private static final GoodsEntity GOODS_ENTITY = getGoodEntity();

    private static final Check CHECK = getCheck();

    private static final CheckEntity CHECK_ENTITY = getCheckEntity();

    private static final OrderEntity ORDER_ENTITY = getOrderEntity();

    private static final Order ORDER = getOrder();

    private static final List<Order> ORDERS = Collections.singletonList(ORDER);

    private static final List<OrderEntity> ORDER_ENTITIES = Collections.singletonList(ORDER_ENTITY);

    private static final User USER_DOMAIN = getUser();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @MockBean
    private CheckRepository checkRepository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private GoodService goodService;

    @MockBean
    private CheckMapper checkMapper;

    @MockBean
    private OrderMapper orderMapper;

    @Autowired
    private CheckServiceImpl service;

    @After
    public void resetMock() {
        reset(checkRepository, orderRepository, goodService, checkMapper, orderMapper);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithIncorrectOrder() {
        exception.expect(InvalidDataRuntimeException.class);

        service.addOrder(null);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithIncorrectQuantInAddCheckspec() {
        exception.expect(InvalidDataRuntimeException.class);
        Order order = ORDER;
        order.setQuant(-100);
        service.addOrder(order);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithIncorrectNdsInAddCheckspec() {
        exception.expect(InvalidDataRuntimeException.class);

        Order order = ORDER;
        order.setNds(-100);
        service.addOrder(order);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithIncorrectNdsInOrder() {
        exception.expect(InvalidDataRuntimeException.class);

        Order order = ORDER;
        order.setNds(1000);
        service.addOrder(order);
    }


    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithNullNdsInAddCheckspec() {
        exception.expect(InvalidDataRuntimeException.class);

        Order order = ORDER;
        order.setNds(null);
        service.addOrder(order);
    }


    @Test
    public void shouldDontFindGoodInThisCheckspec() {
        exception.expect(EntityNotFoundRuntimeException.class);
        when(goodService.findByName(anyString())).thenReturn(null);

        Order order = getOrder();
        order = service.addOrder(order);

        assertThat(null, is(order));
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithNullUserInAddCheck() {
        exception.expect(InvalidDataRuntimeException.class);

        service.addCheck(null, ORDERS);
    }

    @Test
    public void shouldAddCheck() {
        when(checkMapper.checkToCheckEntity(any(Check.class))).thenReturn(CHECK_ENTITY);
        when(checkRepository.save(any(CheckEntity.class))).thenReturn(CHECK_ENTITY);
        when(checkMapper.checkEntityToCheck(any(CheckEntity.class))).thenReturn(CHECK);
        when(orderMapper.orderToOrderEntity(any(Order.class))).thenReturn(ORDER_ENTITY);

        service.addCheck(USER_DOMAIN, ORDERS);

        verify(orderRepository).save(any(OrderEntity.class));
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithNullCheckInFindAllCheckspec() {
        exception.expect(InvalidIdRuntimeException.class);

        service.findOrderByCheck(null);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithInvalidCheckInFindAllCheckspec() {
        exception.expect(InvalidIdRuntimeException.class);

        service.findOrderByCheck(-1L);
    }

    @Test
    public void shouldFindAllCheckspecByCheckId() {
        when(checkMapper.checkEntityToCheck(any(CheckEntity.class))).thenReturn(CHECK);
        when(checkRepository.findById(anyLong())).thenReturn(Optional.ofNullable(CHECK_ENTITY));
        when(checkMapper.checkToCheckEntity(any(Check.class))).thenReturn(CHECK_ENTITY);
        when(orderRepository.findAllByCheck(any(CheckEntity.class))).thenReturn(ORDER_ENTITIES);
        when(orderMapper.orderEntityToOrder(any(OrderEntity.class))).thenReturn(ORDER);

        List<Order> checkspecs = service.findOrderByCheck(CHECK.getId());

        assertThat(checkspecs, is(ORDERS));
    }

    @Test
    public void shouldReturnCheck() {
        when(checkRepository.findById(anyLong())).thenReturn(Optional.of(CHECK_ENTITY));
        when(checkMapper.checkEntityToCheck(any(CheckEntity.class))).thenReturn(CHECK);

        Check check = service.findById(1L);

        verify(checkRepository).findById(anyLong());
        verify(checkMapper).checkEntityToCheck(any(CheckEntity.class));

        assertThat(check, is(CHECK));
    }

    @Test
    public void shouldDontFindCheck() {
        when(checkRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(checkMapper.checkEntityToCheck(any(CheckEntity.class))).thenReturn(null);

        Check check = service.findById(100L);

        assertThat(null, is(check));
        verify(checkRepository).findById(anyLong());
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithNegativeId() {
        exception.expect(InvalidIdRuntimeException.class);

        service.findById(-1L);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithNullId() {
        exception.expect(InvalidIdRuntimeException.class);

        service.findById(null);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithNullCancelCheck() {
        exception.expect(CheckNotExistRuntimeException.class);

        service.cancelCheck(null);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithNullCheckForCancelCheckspec() {
        exception.expect(OrderNotExistRuntimeException.class);

        service.cancelOrder(new ArrayList<>(), 1);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithInvalidCheckpecNumberForCancelCheckspec() {
        exception.expect(OrderNotExistRuntimeException.class);

        service.cancelOrder(ORDERS, -1);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithNullCheckpecNumberForCancelCheckspec() {
        exception.expect(OrderNotExistRuntimeException.class);

        service.cancelOrder(ORDERS, null);
    }

    @Test
    public void shouldCancelCheck() {
        when(checkMapper.checkToCheckEntity(any(Check.class))).thenReturn(CHECK_ENTITY);

        service.cancelCheck(CHECK);

        verify(checkRepository).save(any(CheckEntity.class));
    }

    private static CheckEntity getCheckEntity() {
        CheckEntity check = new CheckEntity();
        check.setTotal(10000.0);
        check.setCanceled(1);
        check.setId(1L);
        check.setRegistration(1);
        check.setDiscount(1);
        return check;
    }

//    @Test
//    public void shouldCancelCheckspec() {
//        when(orderMapper.checkspecToCheckspecEntity(any(Checkspec.class))).thenReturn(ORDER_ENTITY);
//        when(checkMapper.checkToCheckEntity(any(Check.class))).thenReturn(CHECK_ENTITY);
//        when(checkRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        service.cancelCheckSpec(ORDERS, 1, CHECK);
//
//        verify(orderRepository).update(any(CheckspecEntity.class));
//        verify(checkRepository).update(any(CheckEntity.class));
//    }

    private static Check getCheck() {
        Check check = new Check();
        check.setTotal(10000.0);
        check.setCanceled(1);
        check.setId(1L);
        check.setRegistration(1);
        check.setDiscount(1);
        return check;
    }

    private static OrderEntity getOrderEntity() {
        OrderEntity checkspec = new OrderEntity();
        checkspec.setQuant(100.0);
        checkspec.setPrice(100.0);
        checkspec.setTotal(10000.0);
        checkspec.setNdstotal(10000.0);
        checkspec.setNds(100);
        checkspec.setGoods(GOODS_ENTITY);
        checkspec.setCanceled(0);
        return checkspec;
    }

    private static Order getOrder() {
        Order checkspec = new Order();
        checkspec.setQuant(100.0);
        checkspec.setPrice(100.0);
        checkspec.setTotal(10000.0);
        checkspec.setNdstotal(10000.0);
        checkspec.setNds(100);
        checkspec.setGoods(GOOD);
        checkspec.setCanceled(0);
        return checkspec;
    }

    private static Goods getGood() {
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setCode(1);
        goods.setName("Name");
        goods.setQuant(100);
        goods.setPrice(100);
        goods.setMeasure("measure");
        goods.setComments("comments");
        return goods;
    }

    private static GoodsEntity getGoodEntity() {
        GoodsEntity goods = new GoodsEntity();
        goods.setId(1L);
        goods.setCode(1);
        goods.setName("Name");
        goods.setQuant(100);
        goods.setPrice(100);
        goods.setMeasure("measure");
        goods.setComments("comments");
        return goods;
    }

    private static User getUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("email@gmail.com");
        user.setPassword("password");
        user.setName("name");
        return user;
    }
}
