package com.shop.model.service.impl;

import com.shop.model.domain.Goods;
import com.shop.model.entity.GoodsEntity;
import com.shop.model.exception.EntityNotFoundRuntimeException;
import com.shop.model.exception.GoodsIsExistRuntimeException;
import com.shop.model.exception.InvalidDataRuntimeException;
import com.shop.model.repositories.GoodsRepository;
import com.shop.model.service.mapper.GoodMapper;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {GoodsServiceImpl.class})
public class GoodsServiceImplTest {

    private static final Goods GOOD = getGood();

    private static final GoodsEntity ENTITY = new GoodsEntity();

    private static final List<GoodsEntity> GOOD_ENTITIES = Collections.singletonList(ENTITY);

    private static final List<Goods> GOODS = Collections.singletonList(GOOD);

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @MockBean
    private GoodsRepository repository;

    @MockBean
    private GoodMapper mapper;

    @Autowired
    private GoodsServiceImpl service;

    @After
    public void resetMock() {
        reset(repository, mapper);
    }

    @Test
    public void shouldReturnGoodByCode() {
        when(repository.findByCode(anyInt())).thenReturn(Optional.of(ENTITY));
        when(mapper.goodEntityToGood(any(GoodsEntity.class))).thenReturn(GOOD);
        Goods actual = service.findByCode(1);

        verify(repository).findByCode(anyInt());
        verify(mapper).goodEntityToGood(any(GoodsEntity.class));

        assertThat(actual, is(GOOD));
    }

    @Test
    public void shouldThrowEntityNotFoundRuntimeExceptionWithNegativeCode() {
        exception.expect(EntityNotFoundRuntimeException.class);
        exception.expectMessage("Don't find good by this code");

        service.findByCode(-1);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithEmptyBusInAddGood() {
        exception.expect(GoodsIsExistRuntimeException.class);
        exception.expectMessage("Product is null");
        service.addGoods(null);
    }

    @Test
    public void shouldSaveGood() {
        when(repository.findByCode(anyInt())).thenReturn(Optional.empty());
        when(mapper.goodEntityToGood(any(GoodsEntity.class))).thenReturn(GOOD);

        Goods actual = Goods.builder().id(1L).code(1).quant(100).price(10).build();
        service.addGoods(actual);

        verify(repository).save(any());
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionExceptionWithEmptyBusInChangeGood() {
        when(repository.findByCode(anyInt())).thenReturn(Optional.of(ENTITY));
        when(mapper.goodEntityToGood(any(GoodsEntity.class))).thenReturn(GOOD);
        exception.expect(InvalidDataRuntimeException.class);
        exception.expectMessage("Invalid input good data");
        service.changeGoods(1, null, null);
    }

    @Test
    public void shouldChangeGood() {
        when(repository.findByCode(anyInt())).thenReturn(Optional.of(ENTITY));
        when(mapper.goodEntityToGood(any(GoodsEntity.class))).thenReturn(GOOD);

        Goods actual = Goods.builder().id(1L).code(1).quant(500).price(50).build();
        actual.setPrice(10);
        actual.setQuant(100);
        service.changeGoods(1, 100.0, 10.0);
        assertThat(actual, is(GOOD));
        verify(repository).save(any());
    }

    @Test
    public void shouldReturnPagenationGood() {
        PageRequest sortedByCode = PageRequest.of(0, 1, Sort.by("code"));
        when(repository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(GOOD_ENTITIES, sortedByCode, 1));
        when(mapper.goodEntityToGood(any(GoodsEntity.class))).thenReturn(GOOD);

        Page<Goods> pageBus = service.getPageGoods(1, 1);

        Page<Goods> actualPageBus = new PageImpl<>(GOODS, sortedByCode, 1);
        assertThat(pageBus, is(actualPageBus));
    }

    private static Goods getGood() {
        return Goods.builder()
                .id(1L)
                .code(1)
                .quant(100)
                .price(10)
                .build();
    }
}
