package com.microservicios.batch.job;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservicios.batch.domain.Historico;
import com.microservicios.batch.repository.HistoricoRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CompraItemWriter implements ItemWriter<List<Historico>> {
	
    @Autowired
    private HistoricoRepository historicoRepository;


	@Override
	public void write(Chunk<? extends List<Historico>> historicos) throws Exception {
        log.info("All saved ini");    
       for(List<Historico> data: historicos.getItems()) {
    	   for (Historico data2: data) {
    		   historicoRepository.save(data2);
    	   }
       }

        log.info("All saved fin");
	}
}

