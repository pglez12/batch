package com.microservicios.batch.domain;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
 
/**
 * Clase que representa un objeto de datos de compra media.
 * 
 * Esta clase se utiliza para almacenar información agregada sobre las compras,
 * incluyendo la fecha, el ID del evento, el precio medio de las compras y el
 * total de compras realizadas para ese evento en una fecha específica.
 * 
 * @author grupo1
 */
@Entity
@Data
@Builder
public class CompraMedia {
	@Id
	private Date fecha;
	@Id
    private Long eventoid;
    private Double media;
    private Long total;
    
    public CompraMedia() {
    	super();
    }
	public CompraMedia(Date fecha, Long eventoid, Double media, Long total) {
		super();
		this.fecha = fecha;
		this.eventoid = eventoid;
		this.media = media;
		this.total = total;
	}
    
}
