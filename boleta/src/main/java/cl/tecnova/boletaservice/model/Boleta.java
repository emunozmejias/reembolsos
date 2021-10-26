package cl.tecnova.boletaservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.Api;
import lombok.Data;

@Data //lombok @Data encapsula @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
//@Document(collection = "Boleta")Asociado a dependencia mongo
//@Builder  // lombok permite generar los constructores, validar opcion para generar contructor con las demas propiedades de la clase...
@Api
@Entity
public class Boleta {

	@Id
//	@JsonIgnore // el json generado ignorara este field.
//	@ToString.Exclude // se excluira del toStrin/
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long idBoleta;			
	private Date fechaIngreso;
	private Date fechaBoleta;
	private String detalleBoleta;
	private Integer montoBoleta;
	private String observacion;
	
	@Column(name = "id_estado_boleta")
	private Integer idEstadoBoleta;
	
	private Integer idProyecto;
	private String idCentroCosto;
	private Integer idUsuario;
	
	@Column(name = "id_tipo_boleta")
	private Integer idTipoBoleta;
	
	private Integer idSolicitud;

	@ManyToOne
    @JoinColumn(name="id_estado_boleta", insertable= false, updatable = false)
	private Estado estado;
	
	@ManyToOne
    @JoinColumn(name="id_tipo_boleta", insertable= false, updatable = false)
	private TipoBoleta tipoBoleta;
	
	private String numeroDocumento;
	
	public Boleta() {}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Boleta [idBoleta=");
		builder.append(idBoleta);
		builder.append(", fechaIngreso=");
		builder.append(fechaIngreso);
		builder.append(", fechaBoleta=");
		builder.append(fechaBoleta);
		builder.append(", detalleBoleta=");
		builder.append(detalleBoleta);
		builder.append(", montoBoleta=");
		builder.append(montoBoleta);
		builder.append(", observacion=");
		builder.append(observacion);
		builder.append(", idEstadoBoleta=");
		builder.append(idEstadoBoleta);
		builder.append(", idProyecto=");
		builder.append(idProyecto);
		builder.append(", idCentroCosto=");
		builder.append(idCentroCosto);
		builder.append(", idUsuario=");
		builder.append(idUsuario);
		builder.append(", idTipoBoleta=");
		builder.append(idTipoBoleta);
		builder.append(", idSolicitud=");
		builder.append(idSolicitud);
		builder.append(", estado=");
		builder.append(estado);
		builder.append(", tipoBoleta=");
		builder.append(tipoBoleta);
		builder.append("]");
		return builder.toString();
	}
	
}
