package cl.documento.gastoservice.model;

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
public class Documento {

	@Id
	@SequenceGenerator(name ="sequence_documento", sequenceName ="sequence_documento", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_documento")
	private Long idDocumento;
	private byte[] documento;
	private String tipoDocumento;
	private Integer idBoleta;
	
	public Documento() {
		
	}

	@Override
	public String toString() {
		return "Documento [idDocumento=" + idDocumento + ", tipoDocumento=" + tipoDocumento + ", idBoleta=" + idBoleta
				+ "]";
	}
}
