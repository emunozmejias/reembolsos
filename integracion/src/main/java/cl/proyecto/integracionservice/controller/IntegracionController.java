package cl.proyecto.integracionservice.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.proyecto.integracionservice.model.Boleta;
import cl.proyecto.integracionservice.model.FiltroBusqueda;
import cl.proyecto.integracionservice.model.LogReembolsoEntity;
import cl.proyecto.integracionservice.model.LogSolicitud;
import cl.proyecto.integracionservice.model.Solicitud;
import cl.proyecto.integracionservice.model.SolicitudReembolso;
import cl.proyecto.integracionservice.model.TokenSeguridad;
import cl.proyecto.integracionservice.model.User;
import cl.proyecto.integracionservice.service.IntegracionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Component
@RequestMapping("api/integracion")
@Api(value = "Proyecto de Integracion", description = "Proyecto Rest para la integracion entre micro servicios.")
@CrossOrigin
public class IntegracionController {

	private Logger logger = LogManager.getLogger(getClass());

	private ResponseEntity response = null;

	@Autowired
	private IntegracionService integracionService;

	@PostMapping(value = { "/obtieneSolicitudesByFiltro" }, produces = "application/json")
	@ApiOperation(value = "Obtiene todas las solicitudes por filtro determinado", notes = "Se encarga de obtener todas las solicitudes desde base de datos", response = Solicitud.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busqueda de todas las solicitudes exitosa", response = SolicitudReembolso.class) })
	public ResponseEntity<Collection<SolicitudReembolso>> obtieneSolicitudesByFiltro(@RequestBody FiltroBusqueda filtro) {
		return integracionService.obtenerListaSolicitdes(filtro);
	}

	@GetMapping(value = { "/obtieneBoletaSolicitud/{idSolicitud}" }, produces = "application/json")
	@ApiOperation(value = "Obtiene una boleta en base a id de solicitud", notes = "Obtiene una boleta en base a id de solicitud)", response = Boleta.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busqueda de boletas exitosa", response = Boleta.class),
			@ApiResponse(code = 204, message = "Boletas buscadas no existente", response = ResponseEntity.class) })
	public ResponseEntity<Collection<Boleta>> obtieneBoletaByIdSolicitud(@PathVariable("idSolicitud") Long idSolicitud) {
		logger.info("idSolicitud" + idSolicitud);
		return integracionService.obtieneBoletaByIdSolicitud(idSolicitud);
	}

	@GetMapping(value = { "/obtieneBoletaEliminadaSolicitud/{idSolicitud}" }, produces = "application/json")
	@ApiOperation(value = "Obtiene una boleta eliminada en base a id de solicitud", notes = "Obtiene una boleta Eliminada en base a id de solicitud)", response = Boleta.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busqueda de boletas exitosa", response = Boleta.class),
			@ApiResponse(code = 204, message = "Boletas buscadas no existente", response = ResponseEntity.class) })
	public ResponseEntity<Collection<Boleta>> obtieneBoletaEliminadaByIdSolicitud(@PathVariable("idSolicitud") Long idSolicitud) {
		logger.info("idSolicitud" + idSolicitud);
		return integracionService.obtieneBoletaEliminadaByIdSolicitud(idSolicitud);
	}

	@PostMapping(value = { "/obtenerTokenByUser" }, produces = "application/json")
	@ApiOperation(value = "Obtiene el token de acceso", notes = "Se encarga de autenticar al usuario", response = Solicitud.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busqueda de todas las solicitudes exitosa", response = TokenSeguridad.class) })
	public ResponseEntity<TokenSeguridad> obtenerTokenByUser(@Valid @RequestBody User user) {
		logger.info("Entrando a metodo obtenerTokenByUser usuario[" + user.getUsuario() + "]");
		try {
			this.response = integracionService.obtenerTokenByUser(user);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response = new ResponseEntity(HttpStatus.valueOf(500));
		}
		logger.info("Salgo a metodo obtenerTokenByUser usuario[" + user.getUsuario() + "]");
		return response;
	}

	@GetMapping(value = { "/obtieneHistorialSolicitud/{idSolicitud}" }, produces = "application/json")
	@ApiOperation(value = "Obtiene historial de solicitud", notes = "Se encarga de obtener historial de solicitud en base a su id desde base de datos", response = LogSolicitud.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Busqueda de historial de solicitud exitosa", response = LogSolicitud.class) })
	public ResponseEntity<LogSolicitud> obtieneHistorialSolicitud(@PathVariable("idSolicitud") Long idSolicitud) {
		logger.info("Entrando a metodo obtieneHistorialSolicitud");
		response = integracionService.obtieneHistorialSolicitud(idSolicitud);
		logger.info("Salgo de metodo obtieneHistorialSolicitud");
		return response;
	}
}
