package com.microservicios.batch.domain;

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
    @Temporal(TemporalType.DATE)
    @Column(name="timestamp")
    private Date fecha;
}
