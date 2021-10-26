package cl.proyecto.integracionservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.Api;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Api
public class TokenSeguridad {
	private String access_token;
	private int expires_in;
	private int refresh_expires_in;
	private String refresh_token;
	private String token_type;
	private String session_state;
	private String scope;
	private String version;
}
