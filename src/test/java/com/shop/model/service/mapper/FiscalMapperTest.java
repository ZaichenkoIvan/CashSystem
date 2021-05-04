package com.shop.model.service.mapper;

import com.shop.model.domain.Fiscal;
import com.shop.model.entity.FiscalEntity;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;


public class FiscalMapperTest {
    private static final FiscalEntity FISCAL_ENTITY = getFiscalEntity();

    private static final Fiscal FISCAL_DOMAIN = getFiscal();

    private final FiscalMapper fiscalMapper = new FiscalMapper();

    @Test
    public void shouldMapFiscalEntityToFiscal() {
        FiscalEntity actual = fiscalMapper.fiscalToFiscalEntity(FISCAL_DOMAIN);

        assertThat(actual.getId(), is(FISCAL_ENTITY.getId()));
        assertThat(actual.getTotal(), is(FISCAL_ENTITY.getTotal()));
    }

    @Test
    public void shouldMapFiscalToFiscalEntity() {
        Fiscal actual = fiscalMapper.fiscalEntityToFiscal(FISCAL_ENTITY);

        assertThat(actual.getId(), is(FISCAL_DOMAIN.getId()));
        assertThat(actual.getTotal(), is(FISCAL_DOMAIN.getTotal()));
    }

    @Test
    public void mapGoodToGoodEntityShouldReturnNull() {
        FiscalEntity actual = fiscalMapper.fiscalToFiscalEntity(null);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void mapGoodEntityToGoodShouldReturnNull() {
        Fiscal actual = fiscalMapper.fiscalEntityToFiscal(null);

        assertThat(actual, is(nullValue()));
    }

    private static FiscalEntity getFiscalEntity() {
        return new FiscalEntity(1L, 100.0);
    }

    private static Fiscal getFiscal() {
        return Fiscal.builder()
                .id(1L)
                .total(100.0)
                .build();
    }
}
