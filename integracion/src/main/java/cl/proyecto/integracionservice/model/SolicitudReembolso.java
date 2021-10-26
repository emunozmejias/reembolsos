package cl.proyecto.integracionservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.Api;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Api
public class SolicitudReembolso {
	
	private Long idSolicitud;
	private String fechaSolicitud;
	private String observacion;
	private String estadoReembolso;
	private String nombreUsuario;
	private String monto;
}
