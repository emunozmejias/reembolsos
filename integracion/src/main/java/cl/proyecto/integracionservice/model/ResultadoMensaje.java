package cl.proyecto.integracionservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.Api;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Api
public class ResultadoMensaje {

	private String canonical_ids;

	private String success;

	private String failure;

	private Results[] results;

	private String multicast_id;
}
