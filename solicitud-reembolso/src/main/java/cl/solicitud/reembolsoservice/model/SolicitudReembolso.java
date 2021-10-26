package cl.solicitud.reembolsoservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.Api;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Api
@Entity
public class SolicitudReembolso {

	@Id
	@SequenceGenerator(name ="sequence_solicitud",sequenceName ="sequence_solicitud",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_solicitud")
	private Long idSolicitud;
	private Date fechaSolicitud;
	private String observacion;
	
	@Column(name = "id_estado_reembolso")
	private Integer idEstadoReembolso;
	private Integer idUsuario;
	
	
	@ManyToOne
    @JoinColumn(name="id_estado_reembolso", insertable= false, updatable = false)
    private Estado estado;
	
	public SolicitudReembolso() {}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SolicitudReembolso [idSolicitud=");
		builder.append(idSolicitud);
		builder.append(", fechaSolicitud=");
		builder.append(fechaSolicitud);
		builder.append(", observacion=");
		builder.append(observacion);
		builder.append(", idEstadoReembolso=");
		builder.append(idEstadoReembolso);
		builder.append(", idUsuario=");
		builder.append(idUsuario);
		builder.append("]");
		return builder.toString();
	}
}
