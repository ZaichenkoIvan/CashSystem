package com.shop.model.repositories;

import com.shop.model.entity.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoodsRepository extends JpaRepository<GoodsEntity, Long> {

    Optional<GoodsEntity> findByCode(int code);

    Optional<GoodsEntity> findByName(String name);
}
