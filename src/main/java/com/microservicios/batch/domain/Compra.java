package com.microservicios.batch.domain;

import java.util.Date;

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
@Table(name = "compra")
public class Compra {
	@Id
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private double precio;
    private String email;
    private Long eventoid;
}
