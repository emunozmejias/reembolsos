package cl.tecnova.notificaciones.service.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Notificaciones {

	@Id
	@SequenceGenerator(name ="sequence_notificaciones",sequenceName ="sequence_notificaciones",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_notificaciones")
	private Long id;
	
	private Integer idUsuario;
	private String token;
	
	private Date fechaToken;
}
