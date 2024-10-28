package com.microservicios.batch.job;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.microservicios.batch.domain.CompraMedia;
import com.microservicios.batch.domain.Historico;

import lombok.extern.slf4j.Slf4j;

/**
 * Procesador de ítems para convertir una lista de objetos CompraMedia}
 * en una lista de objetos Historico
 * 
 * @author grupo1
 */
@Component
@Slf4j
public class CompraItemProcessor implements ItemProcessor<List<CompraMedia>, List<Historico>> {

    /**
     * Formatea el precio a dos decimales.
     * 
     * @param precio a formatear
     * @return el precio formateado a dos decimales, o 0.0 si el precio es null
     */
    private double formatPrecio(Double precio) {
        if (precio == null) {
            return 0.0;
        }
        BigDecimal bd = BigDecimal.valueOf(precio);
        return bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
  
    /**
     * Procesa una lista de objetos CompraMedia y convierte cada uno en 
     * un objeto Historico.
     * 
     * @param la lista de compras a procesar
     * @return una lista de objetos Historico resultantes del procesamiento
     */  
    @Override
    public List<Historico> process(List<CompraMedia> compras) {
        if (compras == null || compras.isEmpty()) {
            return new ArrayList<>();
        }
        List<Historico> valret = new ArrayList<>();
        for (CompraMedia compra : compras) {
            Historico historico = Historico.builder()
                .eventoid(compra.getEventoid())
                .preciomedio(formatPrecio(compra.getMedia()))
                .numeroventas(compra.getTotal())
                .fecha(new Timestamp(System.currentTimeMillis()))
                .build();
            valret.add(historico);
        }
        log.info("Historico creado con precio");
        return valret;
    }

}
