package com.microservicios.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicios.batch.domain.Historico;

/**
 * Interfaz del repositorio para acceder y manipular entidades de Historico
 * 
 * @author group1
 */
@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Long>{

}
