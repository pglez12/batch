package com.microservicios.batch.listener;

import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

import com.microservicios.batch.domain.Compra;
import com.microservicios.batch.domain.Historico;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CompraItemProcessorListener implements ItemProcessListener<Compra, Historico> {

    @Override
    public void beforeProcess(Compra item) {
        log.info("Procesando item: {}", item);
    }

    @Override
    public void afterProcess(Compra item, Historico result) {
        log.info("Item procesado: {}. Resultado: {}", item, result);
    }

    @Override
    public void onProcessError(Compra item, Exception e) {
        log.error("Error al procesar el item: {}. Error: {}", item, e.getMessage());
    }
}
