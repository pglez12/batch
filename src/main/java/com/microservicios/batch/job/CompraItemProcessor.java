package com.microservicios.batch.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.microservicios.batch.domain.Compra;
import com.microservicios.batch.domain.CompraMedia;
import com.microservicios.batch.domain.Historico;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CompraItemProcessor implements ItemProcessor<List<CompraMedia>, List<Historico>> {
	

    @Override
    public List<Historico> process(List<CompraMedia> compras) {
        if (compras == null || compras.isEmpty()) {
            return null;
        }
        List<Historico> valret = new ArrayList<Historico>();
        for (CompraMedia compra : compras) {
          Historico historico = Historico.builder()
          .eventoid(compra.getEventoid())
          .preciomedio(compra.getMedia())
          .numeroventas(compra.getTotal())
          .fecha(compra.getFecha())
          .build();
          valret.add(historico);
        }
//        log.info("Procesado precios");
//        double sumaPrecios = 0;
//        for (Compra compra : compras) {
//        	log.info("suma = ", sumaPrecios);
//            sumaPrecios += compra.getPrecio();
//        }
//
//        double precioMedio = sumaPrecios / compras.size();
//        Long eventId = compras.get(0).getEventoid();
//        Date today = new Date();
//
//        Historico historico = Historico.builder()
//                .eventoid(eventId)
//                .preciomedio(precioMedio)
//                .numeroventas(compras.size())
//                .fecha(today)
//                .build();
        log.info("Historico creado con precio" );
        return valret;
    }
}
