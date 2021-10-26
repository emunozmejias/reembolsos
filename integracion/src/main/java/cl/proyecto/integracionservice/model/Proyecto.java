package cl.proyecto.integracionservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.Api;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Api
public class Proyecto {

	private int idProyecto;
	private String nombreProyecto;
	private String codigoProyecto;
	private String codigoCentroCosto;
	private Integer presupuesto;
}
