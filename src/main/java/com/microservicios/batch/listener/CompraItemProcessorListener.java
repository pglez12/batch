package com.microservicios.batch.listener;

import java.util.List;

import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

import com.microservicios.batch.domain.Compra;
import com.microservicios.batch.domain.CompraMedia;
import com.microservicios.batch.domain.Historico;

import lombok.extern.slf4j.Slf4j;

/**
 * Listener para el procesamiento de ítems en el job de compras.
 * 
 * @author grupo1
 */
@Component
@Slf4j
public class CompraItemProcessorListener implements ItemProcessListener<List<CompraMedia>, List<Historico>> {

    /**
     * Método llamado antes de procesar un ítem.
     * 
     * @param la lista de objetos CompraMedia que se va a procesar
     */
    @Override
    public void beforeProcess(List<CompraMedia> item) {
        log.info("Procesando item: {}", item);
    }

    /**
     * Método llamado después de procesar un ítem.
     * 
     * @param la lista de objetos CompraMedia que fue procesada
     * @param la lista de objetos Historico resultantes del procesamiento
     */    
    @Override
    public void afterProcess(List<CompraMedia> item, List<Historico> result) {
        log.info("Item procesado: {}. Resultado: {}", item, result);
    }

    /**
     * Método llamado si ocurre un error durante el procesamiento de un ítem.
     * 
     * @param la lista de objetos CompraMedia que se intentó procesar
     * @param la excepción que se lanzó durante el procesamiento
     */
    @Override
    public void onProcessError(List<CompraMedia> item, Exception e) {
        log.error("Error al procesar el item: {}. Error: {}", item, e.getMessage());
    }
}