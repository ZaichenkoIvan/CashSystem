package com.shop.model.service.mapper;

import com.shop.model.domain.Goods;
import com.shop.model.entity.GoodsEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class GoodMapper {

    public Goods goodEntityToGood(GoodsEntity goodsEntity) {

        return Objects.isNull(goodsEntity) ?
                null :
                Goods.builder()
                        .id(goodsEntity.getId())
                        .code(goodsEntity.getCode())
                        .name(goodsEntity.getName())
                        .quant(goodsEntity.getQuant())
                        .price(goodsEntity.getPrice())
                        .measure(goodsEntity.getMeasure())
                        .comments(goodsEntity.getComments())
                        .build();
    }

    public GoodsEntity goodToGoodEntity(Goods goods) {

        return Objects.isNull(goods) ?
                null :
                GoodsEntity.builder()
                        .id(goods.getId())
                        .code(goods.getCode())
                        .name(goods.getName())
                        .quant(goods.getQuant())
                        .price(goods.getPrice())
                        .measure(goods.getMeasure())
                        .comments(goods.getComments())
                        .build();
    }
}
