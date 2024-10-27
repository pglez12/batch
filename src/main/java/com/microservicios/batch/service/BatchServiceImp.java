package com.microservicios.batch.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.microservicios.batch.domain.Compra;
import com.microservicios.batch.domain.Historico;
import com.microservicios.batch.repository.CompraRepository;
import com.microservicios.batch.repository.HistoricoRepository;

@Service
public class BatchServiceImp {

    private static final Logger logger = LoggerFactory.getLogger(BatchServiceImp.class);

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private HistoricoRepository historicoRepository;

    @Scheduled(cron = "0 48 19 * * ?")
    public void processBatch() {
        LocalDate today = LocalDate.now();
        logger.info("Iniciando el proceso batch para la fecha: {}", today);

        // Obtener el rango de fechas para la consulta
        Date startDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Long> eventIds = compraRepository.findDistinctEventIdsByFecha(startDate, endDate);
        logger.info("Event IDs encontrados para la fecha {}: {}", today, eventIds);

        for (Long eventId : eventIds) {
            List<Compra> compras = compraRepository.findByFechaAndEventoid(startDate, endDate, eventId);
            logger.info("Compras encontradas para eventoid {}: {}", eventId, compras.size());

            if (!compras.isEmpty()) {
                double sumaPrecios = 0;
                int numeroVentas = compras.size();

                for (Compra compra : compras) {
                    sumaPrecios += compra.getPrecio();
                    logger.debug("Precio de la compra: {}", compra.getPrecio());
                }

                double precioMedio = sumaPrecios / numeroVentas;
                logger.info("Precio medio para eventoid {}: {}", eventId, precioMedio);

                // Verificar si ya existe un registro en historico para este evento y fecha
                if (historicoRepository.existsByEventoidAndFecha(eventId, startDate)) {
                    logger.info("Ya existe un registro en historico para eventoid {} en fecha {}", eventId, startDate);
                } else {
                    // Crear un nuevo registro en historico
                    Historico historico = Historico.builder()
                            .eventoid(eventId)
                            .preciomedio(precioMedio)
                            .numeroventas(numeroVentas)
                            .fecha(startDate)
                            .build();

                    historicoRepository.save(historico);
                    logger.info("Nuevo registro creado en historico para eventoid {}: {}", eventId, historico);
                }
            } else {
                logger.info("No se encontraron compras para eventoid {}", eventId);
            }
        }
    }
}
