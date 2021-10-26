package cl.proyecto.integracionservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.Api;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Api
public class BoletaOut {

	private Long idBoleta;			
	private String fechaIngreso;
	private String fechaBoleta;
	private String detalleBoleta;
	private String montoBoleta;
	private String observacion;
	private String estadoBoleta;
	private Integer idProyecto;
	private String idCentroCosto;
	private Integer idUsuario;
	private String tipoBoleta;
	private Integer idSolicitud;
	
	private String numeroDocumento;
}
