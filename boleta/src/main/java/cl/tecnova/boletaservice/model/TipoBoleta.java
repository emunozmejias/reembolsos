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
public class TipoBoleta {

	@Id
	@Column(name = "id_tipo")
	private Integer idTipo;
	private String descripcion;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TipoBoleta [idTipo=");
		builder.append(idTipo);
		builder.append(", descripcion=");
		builder.append(descripcion);
		builder.append("]");
		return builder.toString();
	}
}
