package com.shop.model.repositories;

import com.shop.model.entity.FiscalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FiscalRepository extends JpaRepository<FiscalEntity, Long> {

    List<Object[]> createXReport();

    List<Object[]> createZReport();
}
