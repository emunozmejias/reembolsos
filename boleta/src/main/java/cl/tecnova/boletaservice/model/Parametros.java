package cl.tecnova.boletaservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.Api;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Api
@Entity
public class Parametros implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ParametrosPK id;

	@Column(name = "PAR_DESCRIPCION")
	private String descripcion;

	@Column(name = "PAR_ORDEN")
	private Integer orden;

	@Column(name = "PAR_VIGENCIA")
	private String vigencia;

	public ParametrosPK getId() {
		return id;
	}

	public void setId(ParametrosPK id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public String getVigencia() {
		return vigencia;
	}

	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
}
