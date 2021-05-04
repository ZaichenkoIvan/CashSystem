package com.shop.model.repositories;

import com.shop.model.entity.CheckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckRepository extends JpaRepository<CheckEntity, Long> {

}
