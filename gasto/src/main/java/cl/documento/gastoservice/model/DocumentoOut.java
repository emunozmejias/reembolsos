package cl.documento.gastoservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.Api;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Api
public class DocumentoOut {

	private Long idDocumento;
	private String documento;
	private String tipoDocumento;
	private Integer idBoleta;
}
