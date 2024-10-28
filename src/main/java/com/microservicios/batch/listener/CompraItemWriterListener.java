package com.microservicios.batch.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.microservicios.batch.domain.Historico;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CompraItemWriterListener implements ItemWriteListener<Historico>{

    public void beforeWrite(List<?> items) {
        log.info("Preparando para escribir {} items.", items.size());
    }
	
    public void afterWrite(List<?> items) {
        log.info("Items escritos: {}", items);
    }
	
    public void onWriteError(Exception e, List<?> items) {
        log.error("Error al escribir los items: {}. Error: {}", items, e.getMessage());
    }
}
