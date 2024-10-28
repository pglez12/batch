package com.microservicios.batch.repository;

import com.microservicios.batch.domain.Compra;
import com.microservicios.batch.domain.CompraMedia;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Interfaz del repositorio para acceder y manipular entidades de Compra
 * 
 * @author grupo1
 */
@Repository
public interface CompraRepository extends JpaRepository<Compra, Long>{
    
    /**
     * Recupera una lista de objetos CompraMedia que representan datos de compras agregados.
     *
     * Este método calcula el precio promedio y el conteo total de compras para cada 
     * combinación de fecha y ID de evento dentro del rango de fechas especificado.
     *
     * @param startDate la fecha de inicio para la consulta (inclusiva)
     * @param endDate la fecha de fin para la consulta (exclusive)
     * @return una lista de objetos CompraMedia que contienen datos de compras ya calculados para historico
     */
	@Query("SELECT new CompraMedia(c.fecha as fecha, c.eventoid as eventoid, AVG(c.precio) as media, COUNT (*) as total) FROM Compra c WHERE c.fecha >= :startDate AND c.fecha < :endDate GROUP BY c.fecha, c.eventoid")
	List<CompraMedia> findMedia(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
}
