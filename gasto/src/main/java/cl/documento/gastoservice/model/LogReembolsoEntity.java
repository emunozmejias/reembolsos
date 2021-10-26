package cl.documento.gastoservice.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;


/**
 * The persistent class for the log_reembolso database table.
 * 
 */
@Data
@Entity
@Table(name="log_reembolso")
@NamedQuery(name="LogReembolsoEntity.findAll", query="SELECT l FROM LogReembolsoEntity l")
public class LogReembolsoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "LOG_REEMBOLSO_IDLOG_GENERATOR", sequenceName = "SECUENCE_LOG_REEMBOLSO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOG_REEMBOLSO_IDLOG_GENERATOR")
	@Column(name="id_log")
	private Long idLog;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Column(name="id_boleta")
	private Integer idBoleta;

	@Column(name="id_solicitud")
	private Integer idSolicitud;

	@Column(name="log_antes")
	private String logAntes;

	@Column(name="log_despues")
	private String logDespues;

	private Integer usuario;

	public LogReembolsoEntity() {
	}

}