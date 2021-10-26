package cl.tecnova.boletaservice.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.Api;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Api
public class ErrorApi {

	
	private boolean estado;
	private String descripcion;
	
	public ErrorApi() {}
}
