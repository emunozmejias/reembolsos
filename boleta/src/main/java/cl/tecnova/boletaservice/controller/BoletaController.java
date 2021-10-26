package cl.tecnova.boletaservice.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cl.tecnova.boletaservice.model.Boleta;
import cl.tecnova.boletaservice.model.BoletaJson;
import cl.tecnova.boletaservice.model.BoletaOut;
import cl.tecnova.boletaservice.repository.BoletaRepository;
import cl.tecnova.boletaservice.service.BoletaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Component
@RequestMapping("/api/boleta")
@Api(value = "Boleta CRUD", description = "Proyecto Rest para el registro de boletas")
@CrossOrigin
public class BoletaController {

	private Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private BoletaRepository boletaRepository;
	@Autowired
	private BoletaService boletaService;

	private ResponseEntity response = null;

	@GetMapping(value = { "/obtieneAllBoletas" }, produces = "application/json")
	@ApiOperation(value = "Obtiene todas las boletas", notes = "Se encarga de obtener todas las boletas desde base de datos", response = Boleta.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busqueda de todas las boletas exitosa", response = Boleta.class) })
	public ResponseEntity<Collection<Boleta>> obtieneAllBoletas() {
		logger.info("Entrando a metodo obtieneAllboletas");
		response = boletaService.obtieneAllBoletas();
		logger.info("Salgo de metodo obtieneAllboletas");
		return response;
	}

	@GetMapping(value = { "/obtieneBoleta/{idBoleta}" }, produces = "application/json")
	@ApiOperation(value = "Obtiene una boleta en base a su id", notes = "Obtiene una boleta en base a su id", response = Boleta.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busqueda de boleta exitosa", response = Boleta.class),
			@ApiResponse(code = 204, message = "Boleta buscada no existente", response = ResponseEntity.class) })
	public ResponseEntity<Boleta> obtieneBoletaById(@PathVariable("idBoleta") Long idBoleta) {
		logger.info("Entrando a metodo obtieneBoletaById");
		response = boletaService.obtieneBoletaById(idBoleta);
		logger.info("Salgo de metodo obtieneBoletaById");
		return response;
	}

	@PostMapping(value = { "/registraBoleta" }, produces = "application/json")
	@ApiOperation(value = "Ingresa una nueva boleta", notes = "Se encarga de registrar una nueva boleta", response = Boleta.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Boleta registrada exitosamente", response = Boleta.class),
			@ApiResponse(code = 204, message = "Error al crear registro de boleta", response = ResponseEntity.class) })
	public ResponseEntity<?> registraBoleta(@Valid @RequestBody Boleta boleta) {
		logger.info("Entrando a metodo registraBoleta");
		response = boletaService.registraBoleta(boleta);
		logger.info("Salgo de metodo registraBoleta");
		return response;
	}

	@DeleteMapping(value = "/eliminarBoleta/{idBoleta}", produces = "application/json")
	@ApiOperation(value = "Elimina una boleta", notes = "Se encarga de eliminar una boleta logicamente (cambio de estado)", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Eliminacion de boleta exitosa"), @ApiResponse(code = 204, message = "Error al eliminar boleta") })
	public ResponseEntity eliminarBoleta(@PathVariable("idBoleta") Long idBoleta) {
		logger.info("Entrando a metodo eliminarBoleta");
		response = boletaService.eliminarBoleta(idBoleta);
		logger.info("Salgo de metodo eliminarBoleta");
		return response;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/actualizaBoleta/{idBoleta}", method = RequestMethod.PUT)
	@ApiOperation(value = "Actualiza registro de boleta", notes = "Se encarga de actualizar registro de una boleta, boletas con estado eliminada no se pueden actualizar")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Registro de boleta actualizado existosamente", response = Boleta.class),
			@ApiResponse(code = 204, message = "Registro de boleta no encontrado", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "Solicitud incorrecta", response = ResponseEntity.class), // ApiError.class crear clase generica para retornar errores...
	})
	public ResponseEntity<Boleta> actualizaBoleta(@Valid @RequestBody Boleta boleta, @PathVariable("idBoleta") Long idBoleta) {
		logger.info("Entrando a metodo actualizaBoleta");
		response = boletaService.actualizaBoleta(boleta, idBoleta);
		logger.info("Salgo de metodo actualizaBoleta");
		return response;
	}

	// queda a la espera, para pronta modificacion...
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @RequestMapping(value="/asociarSolicitud/{idSolicitud}", method =
	 * RequestMethod.PUT)
	 * 
	 * @ApiOperation(value="Actualiza registro de boleta", notes =
	 * "Se encarga de actualizar registro de una boleta")
	 * 
	 * @ApiResponses(value= {
	 * 
	 * @ApiResponse(code = 200, message =
	 * "Registro de boleta actualizado existosamente", response = Boleta.class),
	 * 
	 * @ApiResponse(code = 204, message = "Registro de boleta no encontrado",
	 * response = ResponseEntity.class),
	 * 
	 * @ApiResponse(code = 404, message = "Solicitud incorrecta", response =
	 * ResponseEntity.class), //ApiError.class crear clase generica para retornar
	 * errores... }) public ResponseEntity<Boleta>
	 * rechazarSolicitudBoleta(@Valid @RequestBody List<Boleta> listaBoleta,
	 * 
	 * @PathVariable("idSolicitud") Long idSolicitud) {
	 * 
	 * return boletaService.asociarSolicitud(listaBoleta, idSolicitud); }
	 */

	// Asociar solicitud en base a idUsuario, idSolicitud, filtro por fecha...
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/asociarSolicitud/{idSolicitud}/{idUsuario}/{fechaSolicitud}", method = RequestMethod.PUT)
	@ApiOperation(value = "Actualiza registro de boleta", notes = "Se encarga de actualizar registro de una boleta, tipos de estado, 1:No rendido; 2:Eliminada; 3:Rendido; 4:Revisada; 5:Rechazada; 6:Pagada")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Registro de boleta actualizado existosamente", response = Boleta.class),
			@ApiResponse(code = 204, message = "Registro de boleta no encontrado", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "Solicitud incorrecta", response = ResponseEntity.class), // ApiError.class crear clase generica para retornar errores...
	})
	public ResponseEntity<Boleta> asociarSolicitud(@PathVariable("idSolicitud") Long idSolicitud, @PathVariable("idUsuario") Long idUsuario, @PathVariable("fechaSolicitud") String fechaSolicitud) {
		logger.info("Entrando a metodo asociarSolicitud");
		response = boletaService.asociarSolicitud(idSolicitud, idUsuario, fechaSolicitud);
		logger.info("Salgo de metodo asociarSolicitud");
		return response;
	}

	@GetMapping(value = { "/obtieneBoletaUsuario/{idUsuario}", "/obtieneBoletaUsuario/{idUsuario}/{estado}" }, produces = "application/json")
	@ApiOperation(value = "Obtiene una boleta en base a id de usuario y estado", notes = "Obtiene una boleta en base a id de usuario y estado de boleta (estado es opcional, tipos de estado, 1:No rendido; 2:Eliminada; 3:Rendido; 4:Revisada; 5:Rechazada; 6:Pagada)", response = Boleta.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busqueda de boleta exitosa", response = Boleta.class),
			@ApiResponse(code = 204, message = "Boleta buscada no existente", response = ResponseEntity.class) })
	public ResponseEntity<Collection<Boleta>> obtieneBoletaByIdUsuario(@PathVariable("idUsuario") Long idUsuario, @PathVariable(value = "estado", required = false) Integer estado) {
		logger.info("Entrando a metodo obtieneBoletaByIdUsuario");
		response = boletaService.obtieneBoletaByIdUsuario(idUsuario, estado);
		logger.info("Salgo de metodo obtieneBoletaByIdUsuario");
		return response;
	}

	@GetMapping(value = { "/obtieneBoletaSolicitud/{idSolicitud}" }, produces = "application/json")
	@ApiOperation(value = "Obtiene una boleta en base a id de solicitud", notes = "Obtiene una boleta en base a id de solicitud)", response = BoletaOut.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busqueda de boletas exitosa", response = BoletaOut.class),
			@ApiResponse(code = 204, message = "Boletas buscadas no existente", response = ResponseEntity.class) })
	public ResponseEntity<Collection<BoletaOut>> obtieneBoletaByIdSolicitud(@PathVariable("idSolicitud") Long idSolicitud) {
		// System.out.println("idSolicitud" + idSolicitud);
		logger.info("Entrando a metodo obtieneBoletaByIdUsuario");
		response = boletaService.obtieneBoletaByIdSolicitud(idSolicitud);
		logger.info("Salgo de metodo obtieneBoletaByIdUsuario");
		return response;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/actualizaEstadoBoleta", method = RequestMethod.PUT)
	@ApiOperation(value = "Actualiza registro de boleta", notes = "Se encarga de actualizar registro de una boleta, boletas con estado eliminada no se pueden actualizar")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Registro de boleta actualizado existosamente", response = Boleta.class),
			@ApiResponse(code = 204, message = "Registro de boleta no encontrado", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "Solicitud incorrecta", response = ResponseEntity.class), // ApiError.class crear clase generica para retornar errores...
	})
	public ResponseEntity<Boleta> actualizaEstadoBoleta(@RequestBody BoletaJson listBoleta) {
		logger.info("Entrando a metodo actualizaEstadoBoleta");
		response = boletaService.actualizaEstadoBoleta(listBoleta);
		logger.info("Salgo de metodo actualizaEstadoBoleta");
		return response;
	}

	@GetMapping(value = { "/obtieneBoletaEliminadaSolicitud/{idSolicitud}" }, produces = "application/json")
	@ApiOperation(value = "Obtiene boletas eliminadas en base a id de solicitud", notes = "Obtiene boletas eliminadas en base a id de solicitud)", response = BoletaOut.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busqueda de boletas exitosa", response = BoletaOut.class),
			@ApiResponse(code = 204, message = "Boletas buscadas no existente", response = ResponseEntity.class) })
	public ResponseEntity<Collection<BoletaOut>> obtieneBoletaEliminadaByIdSolicitud(@PathVariable("idSolicitud") Long idSolicitud) {
		// System.out.println("idSolicitud" + idSolicitud);
		logger.info("Entrando a metodo obtieneBoletaEliminadaByIdSolicitud");
		response = boletaService.obtieneBoletaEliminadaByIdSolicitud(idSolicitud);
		logger.info("Salgo de metodo obtieneBoletaEliminadaByIdSolicitud");
		return response;
	}

}
