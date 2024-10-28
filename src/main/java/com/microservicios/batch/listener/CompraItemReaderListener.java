package com.microservicios.batch.listener;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

import com.microservicios.batch.domain.Compra;
import com.microservicios.batch.domain.CompraMedia;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CompraItemReaderListener implements ItemReadListener<Compra> {

    @Override
    public void beforeRead() {
        log.info("Preparando para leer el siguiente item.");
    }

    @Override
    public void afterRead(Compra item) {
        log.info("Item leído: {}", item);
    }

    @Override
    public void onReadError(Exception e) {
        log.error("Error al leer el item: {}", e.getMessage());
    }
}
