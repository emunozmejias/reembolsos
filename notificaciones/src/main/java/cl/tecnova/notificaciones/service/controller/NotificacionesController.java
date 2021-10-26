package cl.tecnova.notificaciones.service.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.tecnova.notificaciones.service.model.Notificaciones;
import cl.tecnova.notificaciones.service.service.NotificacionesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Component
@RequestMapping("/api/notificaciones")
@Api(value="Notificaciones CRUD", description = "Proyecto Rest para realizar funcionalidades genericas")
@CrossOrigin
public class NotificacionesController {

	private Logger logger = LogManager.getLogger(getClass());
	
	private ResponseEntity response = null;
	
	@Autowired
	private NotificacionesService notificacionesService;
	
	@PostMapping(value = {"/registraToken"}, produces = "application/json")
    @ApiOperation(value = "Ingresa un nuevo privilegio",
            notes = "Se encarga de registrar un nuevo privilegio",
            response = Notificaciones.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Privilegio registrado exitosamente", response = Notificaciones.class),
            @ApiResponse(code = 204, message = "Error al crear registro de privilegio", response = ResponseEntity.class)
    })
	public ResponseEntity<?> registraToken(@Valid @RequestBody Notificaciones notificacionToken) {
		logger.info("Entrando a metodo registraToken");
		response = notificacionesService.registraTokenNotificacion(notificacionToken);
		logger.info("Salgo de metodo registraToken");
		return response;
	}
	
	
	@GetMapping(value = {"/obtieneTokenNotificaciones"}, produces = "application/json")
    @ApiOperation(value = "Obtiene todos los privilegios",
            notes = "Se encarga de obtener todos los privilegios desde base de datos",
            response = Notificaciones.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Busqueda de todos los privilegios exitosa", response = Notificaciones.class)
    })
	public ResponseEntity<Collection<Notificaciones>> obtieneTokenNotificaciones(){
		logger.info("Entrando a metodo obtieneTokenNotificaciones");		
		response = notificacionesService.obtieneTokenNotificaciones();
		logger.info("Salgo de metodo obtieneTokenNotificaciones");
		return response;
	}
	
	@GetMapping(value = {"/obtieneToken/{idUsuario}"}, produces = "application/json")
    @ApiOperation(value = "Obtiene todos los privilegios",
            notes = "Se encarga de obtener todos los privilegios desde base de datos",
            response = Notificaciones.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Busqueda de todos los privilegios exitosa", response = Notificaciones.class)
    })
	public ResponseEntity<Collection<Notificaciones>> obtieneToken(@PathVariable("idUsuario") Integer idUsuario){
		logger.info("Entrando a metodo obtieneToken");		
		response = notificacionesService.obtieneToken(idUsuario);
		logger.info("Salgo de metodo obtieneToken");
		return response;
	}
}
