package com.shop.model.service.impl;

import com.shop.model.domain.Goods;
import com.shop.model.entity.GoodsEntity;
import com.shop.model.exception.EntityNotFoundRuntimeException;
import com.shop.model.exception.GoodsIsExistRuntimeException;
import com.shop.model.exception.InvalidDataRuntimeException;
import com.shop.model.repositories.GoodsRepository;
import com.shop.model.service.GoodService;
import com.shop.model.service.mapper.GoodMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GoodsServiceImpl implements GoodService {

    private final GoodsRepository goodsRepository;
    private final GoodMapper goodMapper;

    public Goods findByCode(int code) {
        return goodMapper.goodEntityToGood(goodsRepository.findByCode(code)

                .orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find good by this code")));
    }

    @Override
    public Goods findByName(String name) {
        return goodMapper.goodEntityToGood(goodsRepository.findByName(name)

                .orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find good by this name")));
    }

    @Override
    public Page<Goods> getPageGoods(int currentPage, int pageSize) {
        PageRequest sortedByCode = PageRequest.of(currentPage - 1, pageSize, Sort.by("code"));
        Page<GoodsEntity> allGoodsEntity = goodsRepository.findAll(sortedByCode);
        List<Goods> result = allGoodsEntity
                .stream()
                .map(goodMapper::goodEntityToGood)
                .collect(Collectors.toList());
        return new PageImpl<>(result, sortedByCode, countAllGoods());
    }

    private long countAllGoods() {
        return goodsRepository.count();
    }

    @Override
    public void addGoods(Goods good) {
        if (Objects.isNull(good)) {
            log.warn("Product is null");
            throw new GoodsIsExistRuntimeException("Product is null");
        }
        Optional<GoodsEntity> findGoodsEntity = goodsRepository.findByCode(good.getCode());

        if (findGoodsEntity.isPresent()) {
            log.warn("Product is exist with this code");
            throw new GoodsIsExistRuntimeException("Product is exist with this code");
        }

        GoodsEntity goodEntity = goodMapper.goodToGoodEntity(good);
        goodsRepository.save(goodEntity);
    }


    @Override
    public void changeGoods(Integer code, Double newQuant, Double newPrice) {
        Goods goods = goodMapper.goodEntityToGood(goodsRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find good by this code")));

        if (Objects.isNull(newQuant) || Objects.isNull(newPrice)) {
            log.warn("Invalid input good data");
            throw new InvalidDataRuntimeException("Invalid input good data");
        }
        goods.setQuant(newQuant);
        goods.setPrice(newPrice);
        GoodsEntity goodsEntity = goodMapper.goodToGoodEntity(goods);
        goodsRepository.save(goodsEntity);
    }
}
