package com.microservicios.batch.repository;

import com.microservicios.batch.domain.Compra;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompraRepository extends JpaRepository<Compra, Long>{

	@Query("SELECT DISTINCT c.eventoid FROM Compra c WHERE c.fecha >= :startDate AND c.fecha < :endDate")
	List<Long> findDistinctEventIdsByFecha(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("FROM Compra c WHERE c.fecha >= :startDate AND c.fecha < :endDate AND c.eventoid = :eventId")
	List<Compra> findByFechaAndEventoid(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("eventId") Long eventId);


}
