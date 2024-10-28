package com.microservicios.batch.listener;

import java.util.List;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

import com.microservicios.batch.domain.Compra;
import com.microservicios.batch.domain.CompraMedia;

import lombok.extern.slf4j.Slf4j;

/**
 * Listener para la lectura de ítems en el proceso de batch.
 * 
 * @author grupo1
 */
@Component
@Slf4j
public class CompraItemReaderListener  implements ItemReadListener<List<CompraMedia>> {

    /**
     * Método que se ejecuta antes de leer el siguiente ítem.
     * Registra un mensaje en los logs indicando que se está
     * preparando para la lectura del próximo ítem.
     */ 
    @Override
    public void beforeRead() {
        log.info("Preparando para leer el siguiente item.");
    }

    /**
     * Método que se ejecuta después de leer un ítem.
     * 
     * @param items la lista de ítems leídos
     */
    @Override
    public void afterRead(List<CompraMedia> items) {
        log.info("Items leídos: {}", items);
    }

    /**
     * Método que se ejecuta en caso de un error al leer un ítem.
     * 
     * @param e la excepción que ocurrió durante la lectura
     */
    @Override
    public void onReadError(Exception e) {
        log.error("Error al leer el item: {}", e.getMessage());
    }
}