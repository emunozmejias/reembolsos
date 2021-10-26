package cl.proyecto.integracionservice.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.Api;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Api
public class LogReembolsoEntity {
	
	private Long idLog;
	private Date fecha;
	private Long idBoleta;
	private Long idSolicitud;
	private String logAntes;
	private String logDespues;
	private Integer usuario;
	private String horaLog;

}
