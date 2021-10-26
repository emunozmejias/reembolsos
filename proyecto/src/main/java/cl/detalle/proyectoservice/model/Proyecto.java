package cl.detalle.proyectoservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.Api;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Api
@Entity
public class Proyecto {

	@Id
	@Column(name = "id_proyecto")
	private int idProyecto;
	
	private String nombreProyecto;
	private String codigoProyecto;
	private String codigoCentroCosto;
	private Integer presupuesto;
	
	public Proyecto() {
		
	}
}
