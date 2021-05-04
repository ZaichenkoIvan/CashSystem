package com.shop.model.service.impl;

import com.shop.model.domain.Fiscal;
import com.shop.model.domain.Report;
import com.shop.model.domain.Report.Detail;
import com.shop.model.repositories.FiscalRepository;
import com.shop.model.service.ReportService;
import com.shop.model.service.mapper.FiscalMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReportServiceImpl implements ReportService {

    private final FiscalRepository fiscalRepository;
    private final FiscalMapper fiscalMapper;

    @Override
    public Report getDataXReport() {
        Report rep = new Report();
        List<Detail> detail = rep.getDetail();
        List<Object[]> xReport = fiscalRepository.createXReport();
        for (Object[] field : xReport) {
            rep.setPrinttime((Timestamp) field[0]);
            rep.setCountCancelCheck((BigInteger) field[1]);
            rep.setCountCheck((BigDecimal) field[2]);
            rep.setSumTotal((Double) field[6]);
            rep.setSumNdsTotal((Double) field[7]);
            detail.add(rep.new Detail((Integer) field[3],
                    (Double) field[4],
                    (Double) field[5]));
        }
        log.info("Х-звіт сформовано");
        return rep;
    }

    @Override
    public Report getDataZReport() {
        Report rep = null;
        List<Object[]> zReport = fiscalRepository.createZReport();
        for (Object[] field : zReport) {
            Fiscal fiscal = new Fiscal();
            fiscal.setTotal((Double) field[6]);
            fiscal = fiscalMapper.fiscalEntityToFiscal(fiscalRepository.save(fiscalMapper.fiscalToFiscalEntity(fiscal)));
            Long repNumber = fiscal.getId();
            rep = new Report().new Builder()
                    .addNumber(repNumber)
                    .addPrinttime((Timestamp) field[0])
                    .addCountCheck((BigDecimal) field[2])
                    .addCountCancelCheck((BigInteger) field[1])
                    .addTotalA(field[3] != null ? (Double) field[3] : 0.0)
                    .addTotalB(field[5] != null ? (Double) field[5] : 0.0)
                    .addTotalC(field[7] != null ? (Double) field[7] : 0.0)
                    .addNdsTotalA(field[4] != null ? (Double) field[4] : 0.0)
                    .addNdsTotalB(field[6] != null ? (Double) field[6] : 0.0)
                    .addNdsTotalC(field[8] != null ? (Double) field[8] : 0.0)
                    .addSumTotal(field[9] != null ? (Double) field[9] : 0.0)
                    .addSumNdsTotal(field[10] != null ? (Double) field[10] : 0.0)
                    .build();
        }
        log.info("Z-звіт сформовано");
        return rep;
    }
}
