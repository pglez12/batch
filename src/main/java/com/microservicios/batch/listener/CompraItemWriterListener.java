package com.microservicios.batch.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.microservicios.batch.domain.Historico;

import lombok.extern.slf4j.Slf4j;

/**
 * Listener para el proceso de escritura de ítems.
 * 
 * @author grupo1
 */
@Component
@Slf4j
public class CompraItemWriterListener implements ItemWriteListener<Historico>{

    /**
     * Método que se invoca antes de escribir los ítems.
     * 
     * @param items la lista de ítems que se van a escribir
     */
    public void beforeWrite(List<?> items) {
        log.info("Preparando para escribir {} items.", items.size());
    }
	
    /**
     * Método que se invoca después de que los ítems han sido escritos.
     * 
     * @param items la lista de ítems que se han escrito
     */
    public void afterWrite(List<?> items) {
        log.info("Items escritos: {}", items);
    }
	
    /**
     * Método que se invoca cuando ocurre un error al escribir los ítems.
     * 
     * @param e el error ocurrido durante la escritura
     * @param items la lista de ítems que se intentaron escribir
     */
    public void onWriteError(Exception e, List<?> items) {
        log.error("Error al escribir los items: {}. Error: {}", items, e.getMessage());
    }
}
