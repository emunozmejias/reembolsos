package cl.datos.datostiposervice.controller;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.datos.datostiposervice.model.Estado;
import cl.datos.datostiposervice.model.Parametros;
import cl.datos.datostiposervice.model.PrivilegioUsuario;
import cl.datos.datostiposervice.model.TipoBoleta;
import cl.datos.datostiposervice.service.DatosTipoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Component
@RequestMapping("/api/datosTipo")
@Api(value = "Datos Tipo CRUD", description = "Proyecto Rest para obtener tipo de datos genericos")
@CrossOrigin
public class DatosTipoController {

	private Logger logger = LogManager.getLogger(getClass());

	private ResponseEntity response = null;

	@Autowired
	private DatosTipoService datosTipoService;

	@GetMapping(value = { "/obtieneAllEstados" }, produces = "application/json")
	@ApiOperation(value = "Obtiene todos los estados", notes = "Se encarga de obtener todos los estado desde base de datos", response = Estado.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busqueda de todos los estados exitosa", response = Estado.class) })
	public ResponseEntity<Collection<Estado>> obtieneAllEstados() {
		logger.info("Entrando a metodo obtieneAllEstados");
		response = datosTipoService.obtieneAllEstados();
		logger.info("Salgo de metodo obtieneAllEstados");
		return response;
	}

	@GetMapping(value = { "/obtieneAllTiposBoleta" }, produces = "application/json")
	@ApiOperation(value = "Obtiene todos los tipos de boleta", notes = "Se encarga de obtener todos los tipo de boleta desde base de datos", response = TipoBoleta.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busqueda de todos los tipos de boleta exitosa", response = TipoBoleta.class) })
	public ResponseEntity<Collection<TipoBoleta>> obtieneAllTiposBoleta() {
		logger.info("Entrando a metodo obtieneAllTiposBoleta");
		response = datosTipoService.obtieneAllTipos();
		logger.info("Salgo de metodo obtieneAllTiposBoleta");
		return response;
	}

	@GetMapping(value = { "/obtienePrivilegioUsuario/{idUsuario}" }, produces = "application/json")
	@ApiOperation(value = "Obtiene privilegios de usuario", notes = "Se encarga de obtener privilegios de usuario en base a id de usuario", response = PrivilegioUsuario.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busqueda de privilegio de usuario", response = PrivilegioUsuario.class) })
	public ResponseEntity<PrivilegioUsuario> obtienePrivilegioUsuario(@PathVariable("idUsuario") Integer idUsuario) {
		logger.info("Entrando a metodo obtienePrivilegioUsuario");
		response = datosTipoService.obtienePrivilegioUsuario(idUsuario);
		logger.info("Salgo de metodo obtienePrivilegioUsuario");
		return response;
	}

	@GetMapping(value = { "/obtieneAllPrivilegioUsuario" }, produces = "application/json")
	@ApiOperation(value = "Obtiene privilegios de usuario", notes = "Se encarga de obtener privilegios de usuario en base a id de usuario", response = PrivilegioUsuario.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busqueda de privilegio de usuario", response = PrivilegioUsuario.class) })
	public ResponseEntity<Collection<PrivilegioUsuario>> obtieneAllPrivilegioUsuario() {
		logger.info("Entrando a metodo obtieneAllPrivilegioUsuario");
		response = datosTipoService.obtieneAllPrivilegioUsuario();
		logger.info("Salgo de metodo obtieneAllPrivilegioUsuario");
		return response;
	}

	@GetMapping(value = { "/obtenerParametros/{tipo}/{codigo}" }, produces = "application/json")
	@ApiOperation(value = "Obtiene parametros", notes = "Se encarga de obtener parametros dado un tipo y un codigo", response = PrivilegioUsuario.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busqueda de parametros", response = PrivilegioUsuario.class) })
	public ResponseEntity<Collection<Parametros>> obtieneParametros(@PathVariable("tipo") String tipo, @PathVariable("codigo") String codigo) {
		logger.info("Entrando a metodo obtieneParametros");
		response = datosTipoService.obtieneParametros(tipo, codigo);
		logger.info("Salgo de metodo obtieneParametros");
		return response;
	}
}
