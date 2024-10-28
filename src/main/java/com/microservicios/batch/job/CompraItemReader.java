package com.microservicios.batch.job;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservicios.batch.domain.CompraMedia;
import com.microservicios.batch.repository.CompraRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa un lector de ítems para el procesamiento de compras.
 * 
 * Esta clase se encarga de leer los datos de compras desde el repositorio
 * de compras, filtrando los registros en un rango de tiempo específico.
 * El lector solo devuelve registros que aún no han sido procesados.
 * 
 * @author grupo1
 */
@Component
@StepScope
@Slf4j
public class CompraItemReader implements ItemReader<List<CompraMedia>> {

    @Autowired
    private CompraRepository compraRepository;

    private final LocalDate today = LocalDate.now();
    private final Set<Long> processedIds = new HashSet<>();

    /**
     * Lee una lista de objetos CompraMedia desde el repositorio.
     * 
     * Este método calcula el rango de fechas para las compras a leer, que es
     * desde ayer a las 23:31 hasta hoy a las 23:30. Filtra las compras
     * ya procesadas, y devuelve las nuevas compras encontradas.
     *
     * @return una lista de objetos CompraMedia que representan las compras 
     *         realizadas en el rango de fechas especificado, o null si no hay nuevas compras.
     * @throws Exception si ocurre un error al leer los datos
     */
    @Override
    public List<CompraMedia> read() throws Exception {
        log.info("CompraItemReader inicializado para la fecha: {}", today);
        
        //Lo comentado es para forzar la fecha para una demo
        LocalDateTime startDateTime = today.minusDays(1).atTime(23, 31);
        LocalDateTime endDateTime = today.atTime(23, 30);
        //LocalDate startDate = LocalDate.of(2024, 10, 28);
        //LocalDate endDate = LocalDate.of(2024, 10, 29);

        //Date startDateTime = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        //Date endDateTime = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date startDate = Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant());

        
        log.info("Leyendo IDs de eventos entre {} y {}", startDate, endDate);
        
       //List<CompraMedia> compras = compraRepository.findMedia(startDateTime, endDateTime);
        List<CompraMedia> compras = compraRepository.findMedia(startDate, endDate);
        
        compras.removeIf(compra -> processedIds.contains(compra.getEventoid()));

        if (compras != null && !compras.isEmpty()) {
            for (CompraMedia compra : compras) {
                processedIds.add(compra.getEventoid());
            }
            return compras;
        } else {
            log.info("No se encontraron compras nuevas para la fecha: {}", today);
            return null; 
        }
    }
}
