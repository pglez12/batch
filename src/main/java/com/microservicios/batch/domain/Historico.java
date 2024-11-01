package com.microservicios.batch.domain;

import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
/**
 * Clase que representa un registro histórico de ventas.
 * 
 * @author grupo1
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "historico")
public class Historico {
	@Id
    private Long eventoid;
    private Double preciomedio;
    private Long numeroventas;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="timestamp")
    private Timestamp fecha;
}
