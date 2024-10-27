package com.microservicios.batch.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicios.batch.domain.Historico;

public interface HistoricoRepository extends JpaRepository<Historico, Long>{

	boolean existsByEventoidAndFecha(Long eventoid, Date fecha);

}
