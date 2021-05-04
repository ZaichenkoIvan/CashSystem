package com.shop.model.service;

import com.shop.model.domain.Goods;
import org.springframework.data.domain.Page;

public interface GoodService {

    Goods findByCode(int code);

    Goods findByName(String name);

    Page<Goods> getPageGoods(int currentPage, int pageSize);

    void addGoods(Goods good);

    void changeGoods(Integer code, Double newQuant, Double newPrice);
}
