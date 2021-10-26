package cl.privilegio.centrocostoservice.model;

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
public class PrivilegioCentroCosto {

	@Id
	@SequenceGenerator(name ="sequence_centro_costo",sequenceName ="sequence_centro_costo",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_centro_costo")
	private Long id;
	
	private Long idUsuario;
	private String idCentroCosto;
}
