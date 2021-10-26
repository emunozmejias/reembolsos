package cl.solicitud.reembolsoservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.Api;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Api
public class FiltroBusqueda {

	private Estado[] estado;

    private String fechaHasta;

    private String idUsuario;

    private String fechaDesde;
}
