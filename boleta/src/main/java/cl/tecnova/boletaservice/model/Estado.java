package cl.tecnova.boletaservice.model;

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
public class Estado {

	@Id
	@Column(name = "id_estado")
	private Integer idEstado;
	private String descripcion;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Estado [idEstado=");
		builder.append(idEstado);
		builder.append(", descripcion=");
		builder.append(descripcion);
		builder.append("]");
		return builder.toString();
	}
}
