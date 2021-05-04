package com.shop.model.repositories;

import com.shop.model.entity.CheckEntity;
import com.shop.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByCheck(CheckEntity check);
}
