package cl.datos.datostiposervice.model;

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
public class PrivilegioUsuario {

	@Id
	private Integer id;
	private Integer idUsuario;
	private String userName;
	private String privilegio;
	private String descripcion;
	private String email;
	
	public PrivilegioUsuario() {}
	
}
