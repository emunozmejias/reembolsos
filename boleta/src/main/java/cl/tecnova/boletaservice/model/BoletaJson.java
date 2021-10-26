package cl.tecnova.boletaservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.Api;
import lombok.Data;

@Data //lombok @Data encapsula @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Api
public class BoletaJson {

	private BoletaUpdate[] listBoleta;
	
}
