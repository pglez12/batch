package com.microservicios.batch.job;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservicios.batch.domain.Compra;
import com.microservicios.batch.domain.CompraMedia;
import com.microservicios.batch.repository.CompraRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@StepScope
@Slf4j
public class CompraItemReader implements ItemReader<List<CompraMedia>> {

	@Autowired
    private CompraRepository compraRepository;

    private final LocalDate today = LocalDate.now();


    @Override
    public List<CompraMedia> read() throws Exception {
    	log.info("CompraItemReader inicializado para la fecha: {}", today);
    	Date startDate = Date.from(today.minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        log.info("Leyendo IDs de eventos entre {} y {}", startDate, endDate);
        List<CompraMedia> compras = (List<CompraMedia>)compraRepository.findMedia(startDate, endDate);
        if (0 < compras.size()) {
            return compras;
        } else {
            return null; 
        }
    }
}
