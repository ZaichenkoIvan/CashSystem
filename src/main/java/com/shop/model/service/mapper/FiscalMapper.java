package com.shop.model.service.mapper;

import com.shop.model.domain.Fiscal;
import com.shop.model.entity.FiscalEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class FiscalMapper {

    public Fiscal fiscalEntityToFiscal(FiscalEntity fiscalEntity) {

        return Objects.isNull(fiscalEntity) ?
                null :
                Fiscal.builder()
                        .id(fiscalEntity.getId())
                        .total(fiscalEntity.getTotal())
                        .build();
    }

    public FiscalEntity fiscalToFiscalEntity(Fiscal fiscal) {
        return Objects.isNull(fiscal) ?
                null :
                FiscalEntity.builder()
                        .id(fiscal.getId())
                        .total(fiscal.getTotal())
                        .build();
    }
}
